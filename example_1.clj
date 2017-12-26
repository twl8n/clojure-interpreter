(ns try
  (:require [clojure.string :as str]
            [clojure.java.shell :as shell]))
(defn subf []
  (print (shell/sh "bash" "-c" "ls -la | grep drw"))
  (prn "first line")
  )

(defn main
  []
  (print (:out (shell/sh "ls" "-l")))
  ;; (print (shell/sh "bash" "-c" "curl http://laudeman.com/ -o index.html"))
  (subf)
  (prn "foo" (str/replace "hello world" #"o" "0"))
  true)

(main)
