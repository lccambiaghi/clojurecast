(ns clojurecast.fable.conversion
  (:require [clojisr.v1.r :refer [r r+ ->code r->clj clj->r discard-all-sessions]]
            [clojisr.v1.require :refer [require-r]]
            [clojisr.v1.applications.plotting :refer [plot->file]]
            [tech.ml.dataset :as ds]))

(require-r '[fable]
           '[tsibble]
           '[fabletools]
           '[dplyr])

(defn ds->tsibble
  [ds {:keys [index key]}]
  (r.tsibble/as-tsibble ds :index index :key key)
  )
