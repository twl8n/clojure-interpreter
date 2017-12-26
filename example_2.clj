(ns try
  (:require [clojure.string :as str]
            [clojure.java.shell :as shell])
  (:import [javax.imageio ImageIO]
           [java.awt.image BufferedImage BufferedImageOp]))

(defn main
  []
  (let [theimage (ImageIO/read (clojure.java.io/as-file "tractor.jpg"))]
    (println {:width (.getWidth theimage) :height (.getHeight theimage)})))

(main)
