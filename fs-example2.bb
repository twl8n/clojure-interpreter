#!/usr/bin/env bb
(require '[babashka.fs :as fs]
         '[babashka.curl :as curl])

(defn tmpdir
  ([tname]
   (if (fs/exists? tname)
     (tmpdir tname 1)
     tname
     ))
  ([tname count]
   (if (fs/exists? (str tname count))
     (tmpdir tname (inc count))
     (str tname count))))

(let [file-glob "*.clj"
      flist (try (fs/list-dir "." file-glob)
                 (catch Exception ee []))]
  (if (empty? flist)
    ;; if no files, warn user then done
    (printf "No %s files found\n" file-glob)
    (do
      (let [mytmp (tmpdir "tmp")]
      (shell/sh "mkdir" mytmp)
      (printf "Copying files to: %s\n" mytmp)
      (doseq [xx (take 5 flist)] 
        (let [orig-name (fs/file-name xx)
              dest-name (str mytmp "/" (nth (re-matches #"(.*)\.clj" orig-name ) 1) "_cp.clj")]
          (printf "copying %s to %s\n" orig-name dest-name)
          (shell/sh "cp" orig-name dest-name)))))))

