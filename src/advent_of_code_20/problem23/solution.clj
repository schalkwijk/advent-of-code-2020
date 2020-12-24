(ns advent-of-code-20.problem23.solution
  (:require [clojure.string :as str]))

(defn- to-int [string]
  (Integer/parseInt string))

(defn- move-cups [{:keys [current-cup cups] :as state}]
  (let [movable-cup-positions (map #(mod % (count cups)) (range (inc current-cup) (+ 4 current-cup)))
        movable-cups (map #(nth cups %) movable-cup-positions)
        new-cups (vec (remove #(contains? (set movable-cups) %) cups))
        new-cups-with-index (map-indexed vector new-cups)
        insertion-point (->> new-cups-with-index
                             (sort-by second)
                             last
                             (or (first (filter #(< (second %) (nth cups current-cup)) (reverse (sort-by second new-cups-with-index)))))
                             first
                             inc)
        new-final-cups (concat (subvec new-cups 0 insertion-point) movable-cups (subvec new-cups insertion-point))
        new-current-cup (->> cups
                             count
                             (mod (inc (first (first (filter #(= (second %) (nth cups current-cup)) (map-indexed vector new-final-cups)))))))]
    (assoc (assoc state :cups new-final-cups) :current-cup new-current-cup)))

(defn part1
  ([input] (part1 input 100))
  ([input moves]
   (let [state {:current-cup 0 :cups (map to-int (str/split (first input) #""))}]
     (reduce (fn [state _] (move-cups state)) state (range 0 moves)))))

(defn- move-cups-improved [{:keys [current-cup cups] :as state}]
  (let [next-cups (rest (reduce (fn [c _] (conj c (get cups (last c)))) [current-cup] (range 0 3)))
        next-smallest (first (filter #(not (contains? (set next-cups) %)) (range (dec current-cup) 0 -1)))
        largest (first (filter #(not (contains? (set next-cups) %)) (range (count cups) (- (count cups) 3) -1)))
        insertion-point (or next-smallest largest)]
    (-> state
        (assoc :current-cup (get cups (last next-cups)))
        (assoc-in [:cups current-cup] (get cups (last next-cups)))
        (assoc-in [:cups insertion-point] (first next-cups))
        (assoc-in [:cups (last next-cups)] (get cups insertion-point)))))

(defn part2
  ([input] (part2 input 10000000))
  ([input moves]
   (let [raw-cups (vec (map to-int (str/split (first input) #"")))
         long-cups (vec (concat raw-cups (range (inc (count raw-cups)) 1000001)))
         cups (into {} (map #(vector %1 (nth long-cups (mod %2 (count long-cups)))) long-cups (range 1 (inc (count long-cups)))))
         state {:current-cup (first long-cups) :cups cups}
         final-cups (:cups (reduce (fn [state move] (move-cups-improved state)) state (range 0 moves)))]
     (* (get final-cups 1) (get final-cups (get final-cups 1))))))
