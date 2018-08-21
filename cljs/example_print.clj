#!/usr/bin/env clojint.sh

(prn "If printf lacks flush, you won't see the next message.")

(require 
    '[goog.string :as gstring]
    '[goog.string.format])

;; https://stackoverflow.com/questions/34667532/clojure-clojurescript-e-g-the-format-function

(defn format
  [fmt & args]
  (apply gstring/format fmt args))

(defn core-printf
  [fmt & args]
  (print (apply format fmt args)))

(defn printf [fmt & args]
  (core-printf fmt args)
  #_(flush))
(printf "Hello world. %s\n" *ns*)
