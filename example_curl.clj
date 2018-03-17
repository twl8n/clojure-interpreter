#!/usr/bin/env clojint.sh
(require '[clojure.java.shell :as shell])

;; Get the first 5 href URLs from a github page.

;; curl defaults to sending the requested URL to stdout. -L will tells curl to follow redirects. Github is
;; https, so this http is redirected. shell/sh returns a map {:exit 0 :out "stdout" :err "stderr"}. Unix exit
;; value zero is successful.

;; We must either use (dorun) or a non-lazy function like (mapv) to force the println side effect, as well as
;; eager evaluation of map and take.

(let [html (shell/sh "bash" "-c" "curl -L http://github.com/twl8n/clojure-interpreter")]
  (dorun (map #(println (second %)) (take 5 (re-seq #"href=\"(.*?)\"" (:out html))))))
