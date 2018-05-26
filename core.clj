(ns my-sketch.core
  (:require [quil.core :as q]))
(defn tree [len]
  (q/line [0 0] [0 len])
  (if (>= len 1)
    (q/with-translation [0 len]
      (q/with-rotation [(q/radians 75)] (tree (/ len 1.618)))
      (q/with-rotation [(q/radians -75)] (tree (/ len 1.618))))))
(defn snowflake [i ang]
  (if (zero? i)
    (do (q/line [0 0] [9 0])
        (q/translate [9 0]))
    (do
      (snowflake (dec i) ang)
      
      (q/rotate (q/radians (- ang)))
      (snowflake (dec i) ang)
      
      (q/rotate (q/radians (* 2 ang)))
      (snowflake (dec i) ang)
      (q/rotate (q/radians (- ang)))
      (snowflake (dec i) ang))))
(defn setup []
  (q/background 55))
(defn draw []
  (q/background 56)
  (q/translate 0 (/ (q/height) 2))
  (snowflake 4 60))

; run sketch
(q/defsketch trigonometry
  :size [500 500]
  :features [:resizable]
  :draw draw
  :setup setup)
