#!/usr/bin/env bb
(ns bb_shell
  (:require [babashka.fs :as fs]
            [clojure.java.shell :as shell]))

;; (require '[babashka.fs :as fs])

(let [flist (fs/list-dir "/Users/abby/src/clojure-interpreter" "*.clj")]
  (doseq [xx flist] (println (str (fs/file-name xx))))
  (println)
  (println (str (fs/home)))
  
  ;; cwd where app was launched?
  (println (str (fs/cwd)))
  
  (doseq [xx (take 5 flist)] (printf "%s" (:out (shell/sh "ls" "-l" (str xx))))))
