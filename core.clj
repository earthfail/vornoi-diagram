(ns my-sketch.core
  (:require [quil.core :as q]
            [quil.middleware :as m]
            [clojure.data.csv :as csv]
            [clojure.java.io :as io]))
                                        ; Draws sphere at point [0 0 0] and 6 cubes around it.
                                        ; You can fly around this objects using navigation-3d.
                                        ; This draw function is fun-mode compatible (it takes state),
                                        ; though it's not used here, but we need fun-mode for navigation-3d.





(defn get-lat-long []
  (def uri "https://earthquake.usgs.gov/earthquakes/feed/v1.0/summary/all_week.csv")
  (with-open [rdr (io/reader uri)]
    (doall 
     (->> (csv/read-csv rdr)
          (map #(subvec % 1 3))
          (map #(map read-string %))
          rest))))
(def data-csv (get-lat-long))
(defn draw [state]
  (q/background 255)
  (q/lights)
  (q/fill 250 100 150)
  ;;  (q/sphere 75)
  (q/fill 150 154 50)
  (doseq [[alpha beta] data-csv]
    (let [r 75
          a (q/radians alpha)
          b (q/radians beta)
          z (* r (q/sin a))
          vsot (* r (q/cos a))
          x (* vsot (q/cos b))
          y (* vsot (q/sin b))]
      (q/point x y z))))
(q/defsketch my-sketch
  :draw draw
  :size [500 500]
  :renderer :p3d
  :navigation-3d {:position [0 0 75]}
                                        ; Enable navigation-3d.
                                        ; Note: it should be used together with fun-mode.
  :middleware [m/fun-mode m/navigation-3d])
