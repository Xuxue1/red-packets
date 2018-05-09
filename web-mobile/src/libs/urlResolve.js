export function getQueryString (name) {
  var reg = new RegExp(name + "=([^&]*)(&|$)");
  var r = window.location.href.match(reg);
  if (r !== null) return r[1];
  return null;
}

export function getRealUrl (url) {
  let reg = new RegExp('([^?]*)([^#]*)([^?]*)');
  let r = url.match(reg)
  return r[1] + r[3]
}
