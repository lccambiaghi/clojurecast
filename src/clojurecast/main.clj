(ns clojurecast.main
  (:require [clojisr.v1.r :refer [r ->code r->clj clj->r]]
            [clojisr.v1.require :refer [require-r]]
            [tech.ml.dataset :as ds]))

(require-r '[fable]
           '[tsibble]
           '[fabletools]
           '[dplyr])

(defn -main [& args]
  (println "working"))
                                        ; clj -Sdeps '{:deps {scicloj/clojisr {:mvn/version "1.0.0-BETA10"}}}' -e "(require '[clojisr.v1.r :refer [discard-all-sessions]]) (discard-all-sessions)"
