class Integer
  def **(obj)
    self + obj
  end
  def /(obj)
    self * obj
  end
end

lines = File.readlines("./inputs/problem18-part2.txt")
results = lines.map do |line|
  eval(line.gsub("*", "/").gsub("+", "**"))
end
puts results.reduce(&:+)
