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
  
  (println (:out (shell/sh "ls" "-l" (str (nth flist 0))))))
