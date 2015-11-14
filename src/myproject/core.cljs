(ns myproject.core
    (:require [reagent.core :as reagent :refer [atom]]
              [goog.events :as events]
              [goog.history.EventType :as EventType])
    (:import goog.History))

(def aBoard [[1 0 0 0]
             [0 0 0 1]
             [0 1 1 1]
             [0 1 1 1]])


(defn findValueAt [row col board]
 (if
   (or (< col 0) (< row 0) (>= row (count board)) (>= col (count (board 0))))
   0
   ((board row) col)))

(map (range -1 2) (range -1 2))

(defn neighbour-count [row col board]
 (+ (findValueAt (dec row) (dec col) board) ; up-left
   (findValueAt (dec row) col board)        ; up
   (findValueAt (dec row) (inc col) board)  ; up-right
   (findValueAt row (dec col) board)        ; left
   (findValueAt row (inc col) board)        ; right
   (findValueAt (inc row) (dec col) board)  ; down-left
   (findValueAt (inc row) col board)        ; down
   (findValueAt (inc row) (inc col) board)  ; down-right
   ))

(defn aliveAtPoint [row col board] (= ((board row) col) 1))

(defn next-cell-state [row col board]
 (let [count (neighbour-count row col board)]
   (if (aliveAtPoint row col board)
     (if (or (< count 2) (> count 3)) 0 1)
     (if (= count 3) 1 0))))

; 0 1
; 1 0


(defn next-board-state [board]
  (.log js/console board)
  (vec (map-indexed
   (fn [row rowCells]
     (vec (map-indexed
       (fn [col _]
         (next-cell-state row col board))
       rowCells)))
   board)))

(def app-state (reagent/atom [[0 0 0 0 0]
                               [0 0 1 0 0]
                               [0 0 1 0 0]
                               [0 0 1 0 0]
                               [0 0 0 0 0]
                               ]))

(defn isAlive [val] (= val 1))

(defn cell [alive?]
  [:div {:style {:backgroundColor (if alive? "#fff" "#222") :margin 5 :height 30 :width 30}} " "])

(defn rowComponent [row] [:div {:style {:display "flex"}} (for [val row] [cell (isAlive val)])])

(defn swapBoard [] (swap! app-state next-board-state))

(defn app []
  (js/setTimeout swapBoard 1000)
  [:div {:style {:backgroundColor "#888"}}
    (for [row @app-state]
      [rowComponent row])
    [:input {
      :type "button"
      :value "Next state"
      :on-click swapBoard }]])

(fn [] x y)

(defn ^:export main [container-id]
  (reagent/render-component [app]
                             (.getElementById js/document container-id)))
