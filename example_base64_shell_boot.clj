#!/usr/bin/env boot
;; You must install boot aka boot-clj for this example.
;; namespace is boot.core 
(set-env! :dependencies '[[org.clojure/data.codec "0.1.1"]])
(require '[clojure.data.codec.base64])

;; => nil
(def foo (clojure.data.codec.base64/encode (into-array Byte/TYPE "this is a test")))
(prn "foo: " (apply str (map char (clojure.data.codec.base64/decode foo))))

(ns example_1
  (:require [clojure.string :as str]
            [clojure.java.shell :as shell]))
;; After calling ns, the name space is example_1, so if you wanted boot.core functions, you'd need to
;; use the full function with namespace, that is: (boot.core/set-env! ...)

(defn subf []
  (println (shell/sh "bash" "-c" "ls -la | grep drw")))

(defn main
  []
  (print (:out (shell/sh "ls" "-l")))
  (subf)
  (prn "foo" (str/replace "hello world" #"o" "0"))
  true)

(main)
