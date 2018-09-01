#!/usr/bin/env clojure
(ns example_serial_1
  (:require [clojure.string :as str]
            [clojure.java.shell :as shell]
            [clojure.java.jdbc :as jdbc]
            [clojure.tools.namespace.repl :as tnr]))



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
