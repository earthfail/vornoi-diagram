(ns my-sketch.core
  (:require [quil.core :as q]
            [quil.middleware :as m]))
(defn rand-point []
  (mapv (partial rand-int) [(q/width) (q/height)]))
(defn closest-point [[x y] statesp]
  (->>
   statesp
   (map (partial apply q/dist x y))
   (zipmap statesp)
   (sort-by val)
   ffirst))
(defn rand-col []
  (into {}
        (for [point (repeatedly 200 rand-point)
              :let [[x y] point
                    c (q/get-pixel x y)]]
          [point c])))
(def uri "/home/surrlim/Pictures/glasses_pencil_shorthair.png")
(defn setup []
  (q/no-loop)
  (let [img (q/load-image uri)]
    (q/image img 0 0)
    {:img img
     :dots (rand-col)}))

(defn draw [{img :img dots :dots}]
  ;; (println "redraw!")
  (q/stroke-weight 5)
  (doseq [[p c] dots]
    (q/stroke c)
    (apply q/point p))
  (q/stroke-weight 1)
  (doseq [rp (repeatedly 100 rand-point)]
    (let [closestp (closest-point rp (keys dots))
          color (dots closestp)]
      (q/stroke-weight 10)
      (q/stroke color)
      (apply q/point rp)))
  (q/save-frame "resource/####.png")
  )

(q/defsketch trigonometry
  :size [1000 1000]
  :draw draw
  :setup setup
  :update identity
  :features [:resizable]
  :mouse-pressed (fn [state {:keys [x y]}] (println x y) state)
  :key-pressed (fn [state {k :key}]
                 (if-let [new-dots
                          (case k
                            :a (q/no-loop)
                            :s (q/start-loop)
                            :d (q/redraw)
                            :c (q/clear)
                            :b (q/image (:img state) 0 0)
                            :r (rand-col)
                            :w (q/save-frame "resource/####.png")
                            nil)]
                   (assoc state :dots new-dots)
                   state))
  :middleware [m/fun-mode])
