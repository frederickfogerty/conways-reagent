(ns myproject.core.test
  (:require-macros [cemerick.cljs.test
                    :refer (is deftest with-test run-tests testing test-var)])
  (:use [jayq.core :only [$ html]])
  (:require [reagent.core :as r :refer [atom]]
            [cemerick.cljs.test :as t]
            [myproject.core :as subject]))

(enable-console-print!)

 (def board1 [[0 1 0 0]
              [0 0 0 1]
              [0 0 0 0]
              [0 1 0 0]])

 (def board1Next [[0 0 0 0]
                  [0 0 0 0]
                  [0 0 0 0]
                  [0 0 0 0]])

 (def board2 [[0 1 0 0]
              [0 1 0 1]
              [0 0 1 0]
              [0 1 0 1]])

 (def board2Next [[0 0 1 0]
                  [0 0 1 0]
                  [0 1 0 1]
                  [0 0 1 0]])

 (deftest neighbour-count-test
   (is (= 1 (subject/neighbour-count 1 1 board1)))
   (is (= 2 (subject/neighbour-count 2 2 board1)))
   (is (= 0 (subject/neighbour-count 3 3 board1)))

   (is (= 3 (subject/neighbour-count 2 1 board2)))
   (is (= 3 (subject/neighbour-count 0 2 board2)))
   (is (= 2 (subject/neighbour-count 2 0 board2))))

 (deftest find-value-at-test
   (is (= 0 (subject/findValueAt 0 0 board2)))
   (is (= 0 (subject/findValueAt -1 0 board2)))
   (is (= 0 (subject/findValueAt 0 -1 board2)))
   (is (= 0 (subject/findValueAt 4 0 board2)))
   (is (= 0 (subject/findValueAt 0 4 board2)))
   (is (= 1 (subject/findValueAt 0 1 board2)))
   (is (= 0 (subject/findValueAt 2 0 board2))))

 (deftest alive-at-point-test
   ; dead
   (is (= false (subject/aliveAtPoint 0 0 board2)))
   ; alive
   (is (= true (subject/aliveAtPoint 0 1 board2))))

 (deftest next-cell-state-test
   (is (= 0 (subject/next-cell-state 0 0 board1)))
   (is (= 0 (subject/next-cell-state 1 1 board1)))
   (is (= 0 (subject/next-cell-state 2 3 board1)))

   ; dies from overcrowding: 4 neighbours
   (is (= 0 (subject/next-cell-state 2 2 board2)))
   ; comes alive: 3 neighbours
   (is (= 1 (subject/next-cell-state 2 1 board2)))
   ; stays dead
   (is (= 0 (subject/next-cell-state 2 0 board2))))


(run-tests)
