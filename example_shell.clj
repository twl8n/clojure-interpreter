(ns example_1
  (:require [clojure.string :as str]
            [clojure.java.shell :as shell]))

(defn subf []
  (println (shell/sh "bash" "-c" "ls -la | grep drw")))

(defn main
  []
  (print (:out (shell/sh "ls" "-l")))
  (subf)
  (prn "foo" (str/replace "hello world" #"o" "0"))
  true)

(main)
