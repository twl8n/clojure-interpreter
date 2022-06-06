#!/usr/bin/env bb
(require '[babashka.fs :as fs]
         '[babashka.curl :as curl])

(let [file-glob "*.clj"
      flist (try (fs/list-dir "/Users/abby/src/clojure-interpreter" file-glob)
                 (catch Exception ee []))]
  ;; if no files, warn user then done
  (if (empty? flist)
    (printf "No %s files found\n" file-glob)
    (doseq [xx (take 5 flist)] (printf "%s" (:out (shell/sh "ls" "-l" (str xx)))))))
