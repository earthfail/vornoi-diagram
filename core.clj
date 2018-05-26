(ns my-sketch.core
  (:require [quil.core :as q]
            [quil.middleware :as m]))
(def size [500 500])

(def states (let [[w h] size 
        ;;; keys are points and vals are rgb colors
                  points (into [] (repeatedly 7 (fn [] (mapv (partial rand-int) [w h]))))
                  colors (into [] (repeatedly 7 (fn [] (mapv (partial rand-int) [255 255 255]))))]
              (zipmap points colors)))
(defn setup []
  (q/background 0))
(defn rand-point []
  (mapv (partial rand-int) [(q/width) (q/height)]))
(defn closest-point [[x y] statesp]
  (->>
   statesp
   (map (partial apply q/dist x y))
   (zipmap statesp)
   (sort-by val)
   ffirst))

(defn draw []
  (q/stroke-weight 5)
  (doseq [[p c] states]
    (apply q/stroke c)
    (apply q/point p))
  (q/stroke-weight 1)
  (doseq [rp (repeatedly 100 rand-point)]
    (let [closestp (closest-point rp (keys states))
          color (states closestp)]
      (apply q/stroke color)
      (apply q/point rp))))

; run sketch
(q/defsketch trigonometry
  :size size
  :draw draw
  :setup setup
  :mouse-pressed #(q/background 0))
