(ns advent-of-code-20.problem3.solution)

(defn- is-tree? [trees [x y]]
  (and (< x (count trees)) (= "#" (nth (nth trees x) (mod y (count (first trees)))))))

(def slopes
  [[1 1] [1 3] [1 5] [1 7] [2 1]])

(defn part1 [trees]
  (let [height (count trees)]
    (->> height
         (range 1)
         (map (fn [row] [row (* 3 row)]))
         (filter (partial is-tree? trees))
         count)))

(defn part2 [trees]
  (let [height (count trees)]
    (apply * (map (fn [[dr dc]] (count (filter (partial is-tree? trees) (map (fn [row] [row (/ (* dc row) dr)]) (range dr height dr))))) slopes))))
