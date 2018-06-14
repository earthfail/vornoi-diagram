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

;; (def states (let [[w h] size 
;;         ;;; keys are points and vals are rgb colors
;;                   points (into [] (repeatedly 7 (fn [] (mapv (partial rand-int) [w h]))))
;;                   colors (into [] (repeatedly 7 (fn [] (mapv (partial rand-int) [255 255 255]))))]
;;               (zipmap points colors)))
(defn setup []
  (let [img (q/resize (q/load-image "cat.jpg") (q/width) (q/height))]
    
    ;; {:img img
    ;;  :dots (into {}
    ;;              (for [x (range 5
    ;;                             (- (q/width) 5) 50)
    ;;                    y (range 5
    ;;                             (- (q/height) 5) 50)
    ;;                    :let [c (q/get-pixel img x y 50 50)]]
    ;;                [[x y] c]))}
    {:img img}
    )
  )

(defn draw [state]
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
  (if (:img state)
    (q/image (:img state) 0 0))
  )
;; (defn interface []
;;   (case (q/key-as-keyword)
;;     :s (q/save-frame "resource/####.jpg")
;;     :d ))
                                        ; run sketch
(q/defsketch trigonometry
  :size size
  :draw draw
  :setup setup
  :update identity
  :middleware [m/fun-mode m/pause-on-error])
