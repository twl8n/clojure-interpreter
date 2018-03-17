#!/usr/bin/env clojint.sh

;; Don't forget to make this file executable:
;; chmod +x example_4.clj
;; Usage: ls -l | example_4.clj

(doseq [line (line-seq (java.io.BufferedReader. *in*))]
   (println line))
