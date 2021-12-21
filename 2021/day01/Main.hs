
part1 acc (y:[]) = acc
part1 acc (x:y:xs)
  | x < y = part1 (acc + 1) (y:xs)
  | otherwise = part1 acc (y:xs)

map3Sum (b:c:[]) = []
map3Sum (a:b:c:xs) = (a + b + c):map3Sum (b:c:xs)

part2 acc = part1 acc . map3Sum

solve input = show [part1 0 input, part2 0 input]

main =
  interact $ solve . fmap read . lines
