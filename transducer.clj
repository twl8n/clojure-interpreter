(ns exptrans)

;; usage:
;; clj transducer.clj

(def myvec (range 11))

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

;; Calling rxr via the transduce in comment will first call rxr with no args returning []
;; Then it is called with the previous return and each element of myvec until myvec is exhausted
;; Finally, it is called with the finaly return as a single arg.
(defn rxr
  ([] (prn "no args") [])
  ([xx] (prn "one arg" xx) xx)
  ([xx yy] (prn "two args" xx yy) (conj xx yy)))

(defn rxn [& args]
  (when (= 2 (count args)) (prn "yy:" (second args)))
  nil)

(comment
  (transduce foo rxr myvec)
  (transduce foo rxn myvec)
  )

(print "thread-last: " (with-out-str (time (def bar-res (doall (bar myvec))))))

(print "       into: " (with-out-str (time (def foo-res-1 (into [] foo myvec)))))

(print "transduce +: " (with-out-str (time (def foo-res-2 (transduce foo + myvec)))))

(print "   oof comp: " (with-out-str (time (def oof-res (doall (oof  myvec))))))


