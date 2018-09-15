#!/usr/bin/env clojure
(ns example_serial_1
  (:require [clojure.string :as str]
            [clojure.java.shell :as shell]
            [clojure.java.jdbc :as jdbc]
            [clojure.tools.namespace.repl :as tnr]))

(def my-vec [1 2 3 4])

(def my-vec [1 2 3 4 5 6 ])

(prn "count of my-vec: "(count my-vec))

(def my-list '(1 2 3 4))
(prn "count of my-vec: "(count my-vec))

(def my-hashmap {:cake "chocolate"
                 :pie "apple"
                 :cookie "peanut butter"
                 :turn-over "apple"
                 :cobbler "peach"})
(prn "The cobbler value is: " (:cobbler my-hashmap))

(def vec-of-maps [{:foo 1 :bar 2} {:foo 3 :bar 5} {:foo 10 :bar 3} {:foo 1 :bar 3}])
(prn "A vector of foo values: " (map :foo vec-of-maps))
(prn "A vector of foo values: " (map #(get % :foo) vec-of-maps))
(prn "A vector of foo values: " (map (fn [xx] (:foo xx)) vec-of-maps))
(prn "A vector of foo values: " (map #(% :foo) vec-of-maps))

(def my-set #{1 2 3 5 4 6})
(prn "only unique values exist in a set: " my-set)

;; This throws a compile time exception. How to demonstrate this with a run-time exception?
;; Or is there a way to catch the compile-time exception, and continue working?
;; Tricky, because the exception is thrown by the reader.
;; (prn "duplicate keys are not allowed in literal sets. You will get an exception: " 
;;      (try
;;        #{1 2 3 3}
;;        (catch Exception e (.toString e)))

(def foo-set (set (map :foo vec-of-maps)))
(prn "set of :foo from vec-of-maps: " foo-set)    


(defn stringify-bytes
  [bytes]
  (->> bytes
       (map (partial format "%02x"))
       (apply str)))

(prn "string of char decimal 1044: " (-> (char 1044) str))
(prn "single char decimal 1044: " (first (-> (char 1044) str)))

(prn (stringify-bytes (-> (char 1044)
                          str
                          (.getBytes "UTF-8"))))

(prn (format "%04x" (int \Д)))

(printf "single char decimal 0xFEFF: .%s.\n" (-> (char 0xFEFF) str))
