/*! For license information please see 4200.e0a9d771.js.LICENSE.txt */
(self.webpackChunkkiut_client=self.webpackChunkkiut_client||[]).push([["4200"],{22955:function(e,n,t){"use strict";t.r(n);var a=t("58865");t("33948"),t("31672"),t("59461"),t("2490"),t("57640"),t("9924"),t("17727");var l=t("85893"),i=t("67294"),o=t("7104"),r=t("89583"),u=t("16356"),d=t("71893"),c=t("13912"),s=t("55693"),p=t("16393"),h=t("27233");function f(){let e=(0,a._)(["\n    display: flex;\n    align-items: center;\n    color: ",";\n    gap: 5px;\n\n    h1 {\n        margin: 0;\n        font-size: 30px;\n        font-weight: bold;\n\n    }\n"]);return f=function(){return e},e}function g(){let e=(0,a._)(["\n    width: 100%;\n    padding: 1rem;\n\n    .MuiDataGrid-columnHeaderTitleContainer {\n        justify-content: center;\n    }\n"]);return g=function(){return e},e}let v=d.default.div(f(),p.mainColor),x=d.default.div(g());n.default=e=>{let{dataForPage:n}=e,{columns:t,title:a,searchUrl:d,getDataUrl:f}=n,[g,M]=(0,i.useState)([]),[m,j]=(0,i.useState)(!1),[C,S]=(0,i.useState)(1e3),[b,k]=(0,i.useState)({page:0,pageSize:30}),w=e=>{let n=[{alph:"A+",Min:95,Max:100},{alph:"A",Min:90,Max:94},{alph:"B+",Min:85,Max:89},{alph:"B",Min:80,Max:84},{alph:"C+",Min:75,Max:79},{alph:"C",Min:70,Max:74},{alph:"D+",Min:65,Max:69},{alph:"D",Min:60,Max:64},{alph:"F",Min:0,Max:59},{alph:"FA",Min:0,Max:0}],t=parseInt(e);return 0!==t?n.find(e=>e.Min<=t):n.find(e=>e.Min<=t&&e.Max<=t)},z=e=>{j(!0),h.default.get(p.BASE_URL+e).then(e=>{var n,t,l,i,o,r,u;S(null===(n=e.data.obj)||void 0===n?void 0:n.totalElements),"Results"===a?M(null==e?void 0:null===(i=e.data)||void 0===i?void 0:null===(l=i.obj)||void 0===l?void 0:null===(t=l.content)||void 0===t?void 0:t.map((e,n)=>{var t;return{...e,count:n+1,grade:null===(t=w(null==e?void 0:e.score))||void 0===t?void 0:t.alph}})):M(null==e?void 0:null===(u=e.data)||void 0===u?void 0:null===(r=u.obj)||void 0===r?void 0:null===(o=r.content)||void 0===o?void 0:o.map((e,n)=>({...e,count:n+1})))}).catch(e=>{}).finally(()=>{j(!1)})};return(0,i.useEffect)(()=>{z(f(b))},[b,a]),(0,l.jsxs)(x,{children:[(0,l.jsxs)(v,{children:[(0,l.jsx)(r.FaClipboardList,{size:25}),(0,l.jsx)("h1",{children:a})]}),(0,l.jsx)(o.Card,{children:(0,l.jsxs)(o.CardContent,{children:[(0,l.jsx)(s.default,{sx:{mb:2},children:(0,l.jsx)(c.default,{setData:M,searchUrl:d,setIsLoading:j})}),(0,l.jsx)(u.DataGrid,{rowCount:C,columns:t,rows:g||[],loading:m,paginationMode:"server",components:{Toolbar:u.GridToolbar},pageSize:b.pageSize,page:b.page,rowsPerPageOptions:[10,30,50,70,100],onPageChange:e=>{k(n=>({...n,page:e}))},onPageSizeChange:e=>{k(n=>({...n,pageSize:e}))},autoHeight:!0,initialState:{columns:{columnVisibilityModel:{currents:!1,finals:!1,retakeN:!1}}}})]})})]})}},13912:function(e,n,t){"use strict";t.r(n),t("33948"),t("57640"),t("9924"),t("17727");var a=t("85893"),l=t("67294"),i=t("27233"),o=t("16393"),r=t("7104"),u=t("79623"),d=t("63750"),c=t("39711");n.default=e=>{let{searchUrl:n,setData:t,setIsLoading:s}=e,p=(0,l.useRef)(null),{headers:h}=(0,o.getHeaders)(),[f,g]=(0,l.useState)(""),v=(0,c.useLocation)(),x=(e,a)=>{i.default.get("".concat(o.BASE_URL).concat(n).concat(e),{headers:h,cancelToken:a.token}).then(e=>{let n=e.data.obj.obj;t(null==n?void 0:n.map((e,n)=>({...e,count:n+1})))}).catch(e=>{i.default.isCancel(e)}).finally(()=>{s(!1)})};(0,l.useEffect)(()=>{let e=i.default.CancelToken.source();return""!==f&&x(f,e),()=>{e.cancel("Operation canceled due to component unmount.")}},[v.key]);let M=e=>{g(e),p.current&&p.current.cancel("Operation canceled due to new request.");let n=i.default.CancelToken.source();p.current=n;let t=setTimeout(()=>{e.length>=3&&(s(!0),x(e,n))},300);return()=>{n&&n.cancel("Operation canceled due to component unmount."),clearTimeout(t)}};return(0,a.jsx)(r.TextField,{placeholder:"Search by ID card",size:"small",value:f,onChange:e=>M(e.target.value),id:"outlined-start-adornmentdfg",sx:{width:"300px"},InputProps:{startAdornment:(0,a.jsx)(u.default,{position:"start",children:(0,a.jsx)(d.BsSearch,{size:20})})}})}}}]);