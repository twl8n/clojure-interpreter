#!/usr/bin/env clojint.sh

;; Don't forget to make this file executable:
;; chmod +x example_pipe2.clj
;; Usage: ls -l | example_pipe2.clj

(doseq [line (line-seq (java.io.BufferedReader. *in*))]
  (let [[[_ hit]] (re-seq #".*\s+(.*?.clj)" line)]
    (when (some? hit)
      (println "hit: " hit))))
