(ns example-send-reducer
  (:require [clj-http.client :as client]))

;; Problems:
;; client throws exception, then dies
;; client throws exception, but keeps living
;; client state is unknown, with or without an exception

;; A "client" is a clojure containing an atom, with an active flag, as well as start and stop fns.
(def init-http-client
  (let [client (atom {:active false})
        stopper (fn [] (reset! client (assoc @client :active false)))
        starter (fn [] (reset! client (assoc @client :active true)))]
    (fn [] (reset! client {:start starter
                           :stop stopper})
      (starter)
      client)))

(defn close-http-client [client]
  ((:stop @client)))

(defn reinit-http-client [client]
  ((:start @client)))

(def foo (init-http-client))
(close-http-client foo)
(reinit-http-client foo)

(defn http-get [client file-name]
  {:status :ok
   :body "hello world"})


(defn retry-on-catch-transducer
  [rf]
  (fn
    ([] (rf))
    ([acc] (rf acc))
    ([acc file-name]
     (loop [client (:client acc)]
       (or (try (rf (assoc acc :client client)
                    file-name)
                (catch Exception _
                  nil))
           (recur (init-http-client)))))))

(defn http-send-reducer
  ([] {:client (init-http-client)
       :result []})
  ([{:keys [client result] :as acc}]
   (close-http-client client)
   result)
  ([{:keys [client result] :as acc} file-name]
   (update acc :result conj (http-get client file-name) ;; Throws an exception on error. Returns the data on success? You could change it to save and return true?
           )))

(def file-name-list ["foo" "bar"])

(comment
  (sequence retry-on-catch-transducer http-send-reducer file-name-list)
  )


