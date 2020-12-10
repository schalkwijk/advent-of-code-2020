(ns advent-of-code-20.problem9.solution
  (:require [clojure.math.combinatorics :refer :all]))

(defn- to-int [string]
  (if (instance? java.math.BigInteger string)
    string
    (BigInteger. string)))

(defn- invalid? [numbers window-length target-index]
  (let [target (nth numbers target-index)
        sums (map (partial apply +) (combinations (subvec numbers (- target-index window-length) target-index) 2))]
    (-> sums
        set
        (contains? target)
        not)))

(defn part1
  ([numbers] (part1 numbers 25))
  ([numbers preamble]
   (let [numbers (map to-int numbers)]
     (nth numbers (first (filter (partial invalid? (vec numbers) preamble) (range preamble (count numbers))))))))

(defn- compute-next-sum [{:keys [numbers] :as state} index]
  (let [current-number (nth numbers index)]
    (-> state
        (update :sum + current-number)
        (assoc :upper-bound index))))

(defn- compute-sum [numbers target starting-index]
  (let [state {:sum 0 :numbers numbers :lower-bound starting-index :upper-bound starting-index}]
    (->> numbers
         count
         (range starting-index)
         (reductions compute-next-sum state)
         (filter #(>= (:sum %) target))
         first)))

(defn part2
  ([numbers] (part2 numbers 25))
  ([numbers preamble]
   (let [numbers (vec (map to-int numbers))
         target (part1 numbers preamble)
         desired-state (->> numbers
                            count
                            (range 0)
                            (map (partial compute-sum numbers target))
                            (filter #(= (:sum %) target))
                            first)
         smallest-vec (subvec numbers (:lower-bound desired-state) (inc (:upper-bound desired-state)))]
     (+ (apply max smallest-vec) (apply min smallest-vec)))))
