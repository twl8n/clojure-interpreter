(ns exptrans
  (:require [clojure.string :as str]
            [clojure.java.shell :as shell]))

(def myvec (range 10000))

(defn bar [xx]
  (->> xx
   (map #(+ 3 %))
   (map #(/ % 10.0))
   (map #(- % 0.1))))

(def foo
  (comp
   (map #(+ 3 %))
   (map #(/ % 10.0))
   (map #(- % 0.1))))

;; comp that is not a transducer. right-to-left execution (opposite of ->>).
;; Requires partial if you want to use map.
(def oof
  (comp
   (partial map #(- % 0.1))
   (partial map #(/ % 10.0))
   (partial map #(+ 3 %))))

(print "thread-last: " (with-out-str (time (def bar-res (doall (bar myvec))))))

(print "       into: " (with-out-str (time (def foo-res-1 (into [] foo myvec)))))

(print "transduce +: " (with-out-str (time (def foo-res-2 (transduce foo + myvec)))))

(print "   oof comp: " (with-out-str (time (def oof-res (doall (oof  myvec))))))


