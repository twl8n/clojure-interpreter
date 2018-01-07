#!/usr/bin/env clojint.sh
(ns import2
  (:import [org.joda.time DateTime Days]))

(println (str (DateTime/now)))

(println (.getDays (Days/daysBetween (DateTime/parse "2018-01-05") (DateTime/parse "2017-01-05"))))

