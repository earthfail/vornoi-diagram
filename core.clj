(ns my-sketch.core
  (:require [quil.core :as q]
            [quil.middleware :as m]))
(def size [500 500])
(defn rand-point []
  (mapv (partial rand-int) [(q/width) (q/height)]))
(defn closest-point [[x y] statesp]
  (->>
   statesp
   (map (partial apply q/dist x y))
   (zipmap statesp)
   (sort-by val)
   ffirst))


(defn setup []
  (let [img (q/resize (q/load-image "cat2.jpg") (q/width) (q/height))]
    
    {:img img}
    )
  )

(defn draw [state]
  (if (:img state)
    (q/image (:img state) 0 0))
  )

(q/defsketch trigonometry
  :size size
  :draw draw
  :setup setup
  :update identity
  :mouse-pressed (fn [state event] (println state) state)
  :middleware [m/fun-mode m/pause-on-error])
