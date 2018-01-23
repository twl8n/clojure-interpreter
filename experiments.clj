;; decorate printf to add flush

;; https://github.com/weavejester/decorate

(defmacro redef
  "Redefine an existing value, keeping the metadata intact."
  [name value]
  `(let [m# (meta #'~name)
         v# (def ~name ~value)]
     (alter-meta! v# merge m#)
     v#))

(defmacro decorate
  "Wrap a function in one or more decorators."
  [func & decorators]
  `(redef ~func (-> ~func ~@decorators)))

(defn wrap-printf [func]
  (fn [& args]
    (apply func args)
    (flush)))

;; (decorate printf wrap-printf)

(defn use-clojint-printf
  "If not for the :refer-clojure statement in ns, re-def-ing printf would cause a warning."
  []
  (def core-printf clojure.core/printf)
  (defn printf [fmt & args]
    (core-printf fmt args)
    (flush)))

(def mbody-function-literal
  `(do (def ~'core-printf clojure.core/printf)
       (def ~'printf #(do 
                        (apply ~'core-printf %1 %&)
                        (flush)))))

(def mbody
  `(do (def ~'core-printf clojure.core/printf)
       (def ~'printf (fn [~'fmt & ~'args]
                       (print (apply format ~'fmt ~'args))
                       (flush)))))
  
(defmacro frob-printf
  "If not for the :refer-clojure statement in ns, re-def-ing printf would cause a warning."
  []
  mbody)
