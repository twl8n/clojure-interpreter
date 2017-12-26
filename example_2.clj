(ns example_2
  (:import [javax.imageio ImageIO]))

;; We don't have to require clojure.java.io because it is required by core.clj. 

;; This example demonstrates :import of java packages. FWIW, the ImageIO/read function is dreadfully slow, and fails
;; to read some jpeg files. If you want to do this for real, install ImageMagick and run the identify utility
;; via a shell call.

(defn main
  []
  (let [theimage (ImageIO/read (clojure.java.io/as-file "tractor.jpg"))]
    (println {:width (.getWidth theimage) :height (.getHeight theimage)})))

(main)
