(ns example-send-reducer
  (:require [clj-http.client :as client]))

;; Problems:
;; client throws exception, then dies
;; client throws exception, but keeps living
;; client state is unknown, with or without an exception

(defn init-http-client []
  )

(defn http-get [client file-name]
  {:status :ok
   :body "hello world"})

(defn close-http-client [client]
  {:result "closed"})

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


