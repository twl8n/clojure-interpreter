#!/usr/bin/env clojint.sh

(prn "If printf lacks flush, you won't see the next message.")
(def core-printf clojure.core/printf)
(defn printf [fmt & args]
  (core-printf fmt args)
  (flush))
(printf "Hello world. %s\n" *ns*)
