#!/usr/bin/env clojint.sh

;; This is simple, and it works. Using (ns ...) is generally better style. See example_import2.clj.

;; Arguably, pointless use of anything, even (ns ) should be avoided, and if your entire script is a single
;; page of code, then I'm a bit unclear which way is better.

(import '[org.joda.time DateTime Days])

(println (str (DateTime/now)))

(println (.getDays (Days/daysBetween (DateTime/parse "2018-01-05") (DateTime/parse "2017-01-05"))))

