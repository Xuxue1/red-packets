export function convertUnit (val, fixed) {
  let unit = ''
  let endVal = 0
  if (val < 1000) {
    endVal = val.toFixed(fixed)
  } else if (val >= 10000 && val < 100000000) {
    endVal = (val / 10000).toFixed(fixed)
    unit = '万'
  } else {
    endVal = (val / 100000000).toFixed(fixed)
    unit = '亿'
  }
  return endVal + unit
}
