(ns clojurecast.main
  (:require [clojisr.v1.r :refer [r ->code r->clj clj->r]]
            [clojisr.v1.require :refer [require-r]]
            [tech.ml.dataset :as ds]))

(require-r '[fable]
           '[tsibble]
           '[fabletools]
           '[dplyr])

;; R code in clj
(->> (r "tsibble::tourism %>%
  filter(Region == \"Melbourne\") %>%
  model(
    ets = ETS(Trips ~ trend(\"A\")),
    arima = ARIMA(Trips)
  )"))

(-> r.tsibble/tourism
    (r.dplyr/filter '(== Region "Melbourne"))
    (r.fabletools/model (r.fable/ETS '(formula Trips (trend "A")))
                        (r.fable/ARIMA '(formula nil Trips)))
    )

;; data R->clj
(defonce tourism-ds
  (-> r.tsibble/tourism
      (r.dplyr/filter '(== Region "Melbourne"))
      r->clj))

(ds/descriptive-stats tourism-ds)

;; data clj->R
(r.tsibble/tsibble :x [1 2 3 4 5] :y [4 5 2 5 8] :index "x")

(-> r.tsibble/tourism
    (r.dplyr/filter '(== Region "Melbourne"))
    r->clj
    clj->r
    (r.tsibble/as-tsibble :index "Quarter" :key '("Region" "State" "Purpose"))
    )

(defn -main [& args]
  (println "working"))
                                        ; https://scicloj.github.io/clojisr/doc/clojisr/v1/codegen-test/
                                        ; https://scicloj.github.io/clojisr/doc/clojisr/v1/titanic0-test/
                                        ; clj -Sdeps '{:deps {scicloj/clojisr {:mvn/version "1.0.0-BETA10"}}}' -e "(require '[clojisr.v1.r :refer [discard-all-sessions]]) (discard-all-sessions)"
                                        ;
                                        ; https://github.com/tidyverts/fable/blob/master/vignettes/fable.Rmd
                                        ; https://github.com/tidyverts/fabletools/blob/master/R/model.R
                                        ;
                                        ; https://github.com/clojuredatascience/ch9-time-series/blob/master/src/cljds/ch9/examples.clj
