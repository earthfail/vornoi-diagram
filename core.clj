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

(def uri "http://www.catbreedselector.com/wp-content/uploads/2015/09/LaPerm-Cat-Images.jpg")
(defn setup []
  (let [img (q/request-image uri)]
    {:img img
     :dots (into {}
                 (for [x (range 5
                                (- (q/width) 5) 50)
                       y (range 5
                                (- (q/height) 5) 50)
                       :let [c (q/get-pixel img x y 50 50)]]
                   [[x y] c]))}))

(defn draw [{img :img dots :dots}]
  ;; (q/stroke-weight 5)
  ;; (doseq [[p c] dots]
  ;;   (apply q/stroke c)
  ;;   (apply q/point p))
  ;; (q/stroke-weight 1)
  ;; (doseq [rp (repeatedly 100 rand-point)]
  ;;   (let [closestp (closest-point rp (keys dots))
  ;;         color (dots closestp)]
  ;;     (apply q/stroke color)
  ;;     (apply q/point rp)))
  (if img
    (q/image img 0 0)))

(q/defsketch trigonometry
  :size size
  :draw draw
  :setup setup
  :update identity
  :features [:no-safe-fns :resizable]
  :mouse-pressed (fn [state event] (println state) state)
  :key-pressed (fn [state event] (println state) state)
  :middleware [m/fun-mode m/pause-on-error])
