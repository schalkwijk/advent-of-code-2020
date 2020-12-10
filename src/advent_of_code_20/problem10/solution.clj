(ns advent-of-code-20.problem10.solution)

(defn to-int [string]
  (Integer/parseInt string))

(defn- build-jolts [numbers]
  (let [numbers (map to-int numbers)]
    (sort (conj (conj numbers 0) (+ (last (sort numbers)) 3)))))

(defn part1 [numbers]
  (let [jolts (build-jolts numbers)]
    (->> jolts
         count
         (range 1)
         (map-indexed (fn [a b] (- (nth jolts b) (nth jolts a))))
         frequencies
         vals
         (apply *))))

(defn- find-nodes [jolts index]
  (let [target (nth jolts index)]
    (if (= (inc index) (count jolts))
      [target []]
      [target (filter #(<= (- % target) 3) (subvec jolts (inc index) (min (count jolts) (+ 4 index))))])))

(def combinations
  (memoize (fn [graph target jolt](if (= jolt target)
              1
              (apply + (map #(combinations graph target %) (get graph jolt)))))))

(defn part2 [numbers]
  (let [jolts (vec (build-jolts numbers))]
    (combinations (into {} (map (partial find-nodes jolts) (range 0 (count jolts)))) (last jolts) 0)))
