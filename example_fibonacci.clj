(defn fibonacci-loop
  "Creates a vector with the first x numbers in the Fibonacci sequence...using a loop"
  [x]
  (when (= x 0)
    (def fibby []))
  (when (= x 1)
    (def fibby [0]))
  (when (>= x 2)
    (def fibby [0 1]))
  (when (> x 2)  
   (loop [iteration 3]
     (def fibby (into fibby [(+ (last fibby) (second (reverse fibby)))]))
     (when (< iteration x)
       (recur (inc iteration)))))
  fibby)

(defn fibonacci-recursive
  "Creates a vector with the first x numbers in the Fibonacci sequence...but does it recursively"
  ([x]
   (when (= x 0)
     (def fibby []))
   (when (= x 1)
     (def fibby [0]))
   (when (= x 2)
     (def fibby [0 1]))
   (when (> x 2)
     (def fibby (fibonacci-recursive x [0 1])))
   fibby)
  ([x fibby]
   (if (> x 2)
     (fibonacci-recursive (- x 1) (into fibby [(+ (last fibby) (second (reverse fibby)))]))
     fibby)))