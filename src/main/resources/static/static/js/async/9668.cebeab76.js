/*! For license information please see 9668.cebeab76.js.LICENSE.txt */
(self.webpackChunkkiut_client=self.webpackChunkkiut_client||[]).push([["9668"],{26571:function(e,n,t){"use strict";t.r(n);var l=t("58865");t("33948"),t("17727");var i=t("85893"),o=t("67294"),r=t.n(o),d=t("71893"),a=t("57024"),u=t("44118"),s=t("52861"),c=t("27233"),f=t("16393"),v=t("91793"),x=t("30381"),h=t.n(x),p=t("96413"),m=t("73871"),g=t("14628"),j=t("53432");function w(){let e=(0,l._)(["\n    width: 300px;\n    ","\n"]);return w=function(){return e},e}function b(){let e=(0,l._)(["\n    margin: 15px 0 0;\n    padding: 10px 0;\n    display: flex;\n    flex-wrap: wrap;\n    align-items: center;\n    gap: 25px;\n    justify-content: start;\n    ","\n"]);return b=function(){return e},e}function D(){let e=(0,l._)(["\n    width: 100%;\n    display: flex;\n    flex-direction: column;\n    gap: 20px;\n    padding: 1rem;\n\n"]);return D=function(){return e},e}let y=d.default.div(w(),(0,a.extrasmall)({width:"80%"})),k=d.default.div(b(),(0,a.extrasmall)({justifyContent:"center"})),C=d.default.div(D());n.default=()=>{let[e,n]=(0,o.useState)([]),[t,l]=(0,o.useState)([]),[d,a]=(0,o.useState)([]),[x,w]=(0,o.useState)(null),[b,D]=(0,o.useState)(!1),[T,Y]=(0,o.useState)({date:new Date,dateFrom:null,dateTo:null,weekNumber:null,year:null,weekday:null}),S=e=>{let n=h()(e.format("YYYY-MM-DD")).isoWeek(),t=h()(e.format("YYYY-MM-DD")).day(),l=h()(e.format("YYYY-MM-DD")).year(),i=h()(e.format("YYYY-MM-DD")).startOf("isoWeek").toDate(),o=h()(e.format("YYYY-MM-DD")).endOf("isoWeek").toDate();Y({date:e,dateFrom:i,dateTo:o,weekNumber:n,year:l,weekday:t})},{headers:M}=(0,f.getHeaders)();(0,o.useEffect)(()=>{c.default.get(f.BASE_URL+"/room/getRoomsForSelect",{headers:M}).then(e=>{var t;n(null===(t=e.data)||void 0===t?void 0:t.obj)}).catch(e=>{})},[]);let z=()=>{x&&T.weekday&&(D(!0),c.default.get(f.BASE_URL+"/timeTableByWeekOfYear/getTimeTableByRoom?r="+x+"&week="+T.weekNumber+"&weekday="+T.weekday+"&year="+T.year,{headers:M}).then(e=>{var n,t;l(null==e?void 0:null===(n=e.data)||void 0===n?void 0:n.obj),a(null==e?void 0:null===(t=e.data)||void 0===t?void 0:t.secondObj)}).catch(e=>{}).finally(()=>{D(!1)}))};(0,o.useEffect)(()=>{z()},[x,T]);let[_,N]=r().useState(h()());return(0,o.useEffect)(()=>{S(_)},[_]),(0,i.jsxs)(C,{children:[(0,i.jsxs)(k,{children:[(0,i.jsx)(y,{children:(0,i.jsx)(u.default,{disablePortal:!0,id:"combo-box-demo",options:e,onChange:(e,n)=>{w(null==n?void 0:n.label)},sx:{width:300},renderInput:e=>(0,i.jsx)(s.default,{...e,label:"Auditory"})})}),(0,i.jsx)(y,{children:(0,i.jsx)(m.LocalizationProvider,{dateAdapter:p.AdapterMoment,children:(0,i.jsx)(g.DesktopDatePicker,{label:"Day",inputFormat:"DD/MM/YYYY",value:_,onChange:e=>{N(e)},renderInput:e=>(0,i.jsx)(s.default,{sx:{width:"300px !important"},...e})})})})]}),b?(0,i.jsx)(j.default,{}):(0,i.jsx)(v.default,{data:t,statistics:d,objWeek:T})]})}},98880:function(e,n,t){"use strict";t.r(n);var l=t("58865");t("31672"),t("59461"),t("2490"),t("88449"),t("19894"),t("57640"),t("9924");var i=t("85893"),o=t("67294"),r=t("7104"),d=t("71893"),a=t("26178"),u=t("78951"),s=t("30381"),c=t.n(s),f=t("50533"),v=t("5434"),x=t("51649");function h(){let e=(0,l._)(["\n    color: ",";\n    display: flex;\n    align-items: center;\n    justify-content: center;\n    gap: 5px;\n"]);return h=function(){return e},e}let p=(0,o.forwardRef)(function(e,n){return(0,i.jsx)(r.Slide,{direction:"up",ref:n,...e})}),m=(0,a.createTheme)({components:{MuiTableCell:{styleOverrides:{root:{justifyContent:"center",textAlign:"center",border:"1px solid #ccc",padding:"10px"}}}}}),g=d.default.span(h(),e=>e.color?"green":"red");n.default=(0,o.memo)(e=>{var n,t,l;let{open:o,handleClose:d,data:s}=e,h=(0,f.useSelector)(e=>e.hourSection),j=e=>{var n,t,l;let o=null===(n=e.teachers[0])||void 0===n?void 0:n.shortName,r=null==e?void 0:e.hourNumber,d=null===(t=h.find(e=>e.number===r))||void 0===t?void 0:t.start,a=null==s?void 0:s.timesTouchFilter.filter(e=>(null==e?void 0:e.section)===r&&(null==e?void 0:e.login)===o).sort((e,n)=>new Date(e.time)-new Date(n.time));if(!a||0===a.length)return(0,i.jsx)(x.IoMdCloseCircle,{size:20,color:"red"});let u=null===(l=a[0])||void 0===l?void 0:l.time,f=new Date(d).setDate(new Date(u).getDate())>=u;return(0,i.jsxs)(g,{color:f,children:[f?(0,i.jsx)(v.MdOutlineAddCircleOutline,{size:16}):(0,i.jsx)(v.MdRemoveCircleOutline,{size:16}),(0,i.jsx)("p",{children:c()(Math.abs(new Date(d).setDate(new Date(u).getDate())-u)).format("mm:ss")})]})};return o?(0,i.jsxs)(r.Dialog,{open:o,maxWidth:"md",TransitionComponent:p,keepMounted:!0,onClose:d,"aria-labelledby":"selected-lesson-dialog-title",fullWidth:!0,children:[(0,i.jsxs)(r.DialogTitle,{id:"selected-lesson-dialog-title",children:[null==s?void 0:null===(n=s.hourItemFilter[0])||void 0===n?void 0:n.room," ",null==s?void 0:null===(t=s.hourItemFilter[0])||void 0===t?void 0:t.periodStartAndEndTime]}),(0,i.jsx)(r.DialogContent,{sx:{padding:"0 5px"},children:(0,i.jsx)(a.ThemeProvider,{theme:m,children:(0,i.jsx)(r.TableContainer,{component:r.Paper,children:(0,i.jsxs)(r.Table,{sx:{minWidth:450},"aria-label":"selected-hour-lessons",children:[(0,i.jsx)(r.TableHead,{children:(0,i.jsxs)(r.TableRow,{children:[(0,i.jsx)(r.TableCell,{children:(0,i.jsx)("b",{children:"Teacher"})}),(0,i.jsx)(r.TableCell,{children:(0,i.jsx)("b",{children:"Subjects"})}),(0,i.jsx)(r.TableCell,{children:(0,i.jsx)("b",{children:"Attendance"})})]})}),(0,i.jsx)(r.TableBody,{children:(null==s?void 0:null===(l=s.hourItemFilter)||void 0===l?void 0:l.length)?s.hourItemFilter.map((e,n)=>(0,i.jsxs)(r.TableRow,{children:[(0,i.jsx)(r.TableCell,{children:null==e?void 0:e.teacherName}),(0,i.jsx)(r.TableCell,{children:null==e?void 0:e.lessonName}),(0,i.jsx)(r.TableCell,{children:j(e)})]},n)):(0,i.jsx)(r.TableRow,{children:(0,i.jsx)(r.TableCell,{colSpan:3,children:(0,i.jsx)(u.default,{w:200,h:180})})})})]})})})}),(0,i.jsx)(r.DialogActions,{children:(0,i.jsx)(r.Button,{size:"small",variant:"outlined",onClick:d,children:"Close"})})]}):null})},91793:function(e,n,t){"use strict";t.r(n);var l=t("58865");t("33948"),t("57640"),t("9924"),t("31672"),t("59461"),t("2490"),t("88449"),t("19894");var i=t("85893"),o=t("67294"),r=t("71893"),d=t("5434"),a=t("30381"),u=t.n(a),s=t("50533"),c=t("98880");function f(){let e=(0,l._)(["\n    color: ",";\n    position: absolute;\n    top: 40%;\n    right: -26.3px;\n    font-size: 11px;\n    rotate: 90deg;\n"]);return f=function(){return e},e}function v(){let e=(0,l._)(["\n    display: flex;\n    align-items: start;\n    font-size: ",";\n    text-align: start;\n    font-weight: 600;\n    letter-spacing: 0.8px;\n"]);return v=function(){return e},e}function x(){let e=(0,l._)(["\n    display: flex;\n    align-items: center;\n    justify-content: center;\n    font-size: ",";\n    text-align: center;\n    font-weight: 600;\n    letter-spacing: 0.8px;\n"]);return x=function(){return e},e}function h(){let e=(0,l._)(["\n    font-size: 12px;\n    font-weight: 300;\n"]);return h=function(){return e},e}function p(){let e=(0,l._)(["\n    width: 100%;\n    height: 180px;\n    display: flex;\n    border-top: 0.1px solid #cacaca;\n"]);return p=function(){return e},e}function m(){let e=(0,l._)(["\n    width: 100%;\n    display: flex;\n    flex-direction: column;\n    background-color: #fff;\n    font-size: 14px;\n    font-weight: 600;\n    letter-spacing: 0.2px;\n"]);return m=function(){return e},e}function g(){let e=(0,l._)(["\n    flex: ",";\n    position: relative;\n    display: flex;\n    align-items: center;\n    justify-content: center;\n    flex-direction: column;\n    gap: 8px;\n    border-right: 0.1px solid #cacaca;\n    font-size: 9px;\n    padding: 10px !important;\n\n    background-color: ",";\n    color: ",";\n\n"]);return g=function(){return e},e}function j(){let e=(0,l._)(["\n    cursor: pointer;\n    color: #fff;\n    width: 23px;\n    height: 23px;\n    position: absolute;\n    left: 10px;\n    bottom: 10px;\n    font-size: 12px;\n    border-radius: 50%;\n    background-color: rgba(0, 0, 255, 0.75);\n    display: flex;\n    justify-content: center;\n    align-items: center;\n    text-align: center;\n    line-height: 1;\n"]);return j=function(){return e},e}function w(){let e=(0,l._)(["\n    width: 100%;\n    display: flex;\n    align-items: center;\n    justify-content: center;\n    flex-direction: column;\n    gap: 20px;\n    position: relative;\n"]);return w=function(){return e},e}function b(){let e=(0,l._)(["\n    width: 100%;\n    height: 60px;\n    display: flex;\n    background-color: #B4E2FB;\n    font-size: 14px;\n    font-weight: 600;\n    letter-spacing: 0.2px;\n"]);return b=function(){return e},e}function D(){let e=(0,l._)(["\n    overflow: hidden;\n    margin: 0 auto;\n    width: 1800px;\n    border-radius: 7px;\n    box-shadow: rgba(99, 99, 99, 0.2) 0 2px 8px 0;\n"]);return D=function(){return e},e}function y(){let e=(0,l._)(["\n    width: 100%;\n    overflow: scroll;\n"]);return y=function(){return e},e}let k=r.default.span(f(),e=>(null==e?void 0:e.color)?"green":"red"),C=r.default.span(v(),e=>e.fontSize||"7px"),T=r.default.span(x(),e=>e.fontSize||"7px"),Y=r.default.span(h()),S=r.default.div(p()),M=r.default.div(m()),z=r.default.span(g(),e=>e.flex,e=>(null==e?void 0:e.bg)?"red":"white",e=>(null==e?void 0:e.bg)?"white":"#000"),_=r.default.span(j()),N=r.default.span(w()),O=r.default.div(b()),A=r.default.div(D()),R=r.default.div(y());n.default=e=>{var n,t,l,r,a;let{data:f,statistics:v,objWeek:x}=e,[h,p]=(0,o.useState)(!1),[m,g]=(0,o.useState)(null),j=(0,s.useSelector)(e=>e.hourSection),w=u()(new Date).isoWeek(),b=e=>{g(e),p(!0)};return(0,i.jsxs)(R,{children:[(0,i.jsxs)(A,{children:[(0,i.jsxs)(O,{children:[(0,i.jsx)(z,{flex:1,children:"TIME"}),null==j?void 0:j.map((e,n)=>(0,i.jsx)(z,{flex:2,children:null==e?void 0:e.hour},n))]}),(0,i.jsx)(M,{children:(0,i.jsxs)(S,{children:[(0,i.jsx)(z,{flex:1,children:(0,i.jsx)(Y,{children:f&&(null===(t=f[0])||void 0===t?void 0:null===(n=t.room)||void 0===n?void 0:n.length)===7?null===(l=f[0])||void 0===l?void 0:l.room:null===(a=f[0])||void 0===a?void 0:null===(r=a.room)||void 0===r?void 0:r.substring(0,5)})}),null==j?void 0:j.map((e,n)=>{var t,l,o,r,a,s,c,h,p,m,g,j,D;let y=null==f?void 0:f.find(n=>(null==n?void 0:n.hourNumber)===(null==e?void 0:e.number)),Y=null==f?void 0:f.filter(n=>(null==n?void 0:n.hourNumber)===(null==e?void 0:e.number)),S=null==v?void 0:null===(t=v.filter(n=>(null==n?void 0:n.section)===(null==e?void 0:e.number)&&(null==n?void 0:n.login)===y.teachers[0].shortName))||void 0===t?void 0:t.sort(function(e,n){return(null==e?void 0:e.time)>(null==n?void 0:n.time)?1:(null==e?void 0:e.time)<(null==n?void 0:n.time)?-1:0}),M=null==v?void 0:v.filter(n=>(null==n?void 0:n.section)===(null==e?void 0:e.number)),O=new Date(null==e?void 0:e.start);return O.setDate(O.getDate()-O.getDay()+u()().day()),y?(0,i.jsxs)(z,{flex:2,bg:(void 0===S||(null==S?void 0:S.length)===0)&&((null==x?void 0:x.weekday)<=new Date().getDay()&&((null==e?void 0:e.start)<new Date||O<new Date)||x.weekNumber<w||x.year<new Date().getFullYear()),children:[(null==Y?void 0:Y.length)>1&&(0,i.jsx)(_,{onClick:()=>b({hourItemFilter:Y,timesTouchFilter:M}),children:null==Y?void 0:Y.length}),(0,i.jsxs)(N,{children:[(0,i.jsx)(T,{fontSize:15,children:y.lessonName}),S&&(null==S?void 0:S.length)!==0&&(0,i.jsxs)(k,{color:new Date(null==e?void 0:e.start).setDate(new Date(null===(l=S[0])||void 0===l?void 0:l.time).getDate())>=(null===(o=S[0])||void 0===o?void 0:o.time),children:[new Date(null==e?void 0:e.start).setDate(new Date(null===(r=S[0])||void 0===r?void 0:r.time).getDate())>=(null===(a=S[0])||void 0===a?void 0:a.time)?(0,i.jsx)(d.MdOutlineAddCircleOutline,{size:16}):(0,i.jsx)(d.MdRemoveCircleOutline,{size:16})," ",u()(new Date(new Date(null==e?void 0:e.start).setDate(new Date(null===(s=S[0])||void 0===s?void 0:s.time).getDate())>=(null===(c=S[0])||void 0===c?void 0:c.time)?new Date(null==e?void 0:e.start).setDate(new Date(null===(h=S[0])||void 0===h?void 0:h.time).getDate())-(null===(p=S[0])||void 0===p?void 0:p.time):(null===(m=S[0])||void 0===m?void 0:m.time)-new Date(null==e?void 0:e.start).setDate(new Date(null===(g=S[0])||void 0===g?void 0:g.time).getDate()))).format("mm:ss")]}),(void 0===S||(null==S?void 0:S.length)===0)&&(x.weekday<=new Date().getDay()&&((null==e?void 0:e.start)<new Date||O<new Date)||(null==x?void 0:x.weekNumber)<w)&&(0,i.jsxs)(k,{color:!1,children:[(0,i.jsx)(d.MdRemoveCircleOutline,{size:12})," "," "," 50:00"]}),(0,i.jsx)(C,{fontSize:10,children:(null==y?void 0:null===(j=y.teacherName)||void 0===j?void 0:j.length)>1?null==y?void 0:null===(D=y.teacherName)||void 0===D?void 0:D.join("/ "):null==y?void 0:y.teacherName[0]})]})]},n):(0,i.jsx)(z,{flex:2,children:" - "},n)})]})})]}),(0,i.jsx)(c.default,{open:h,handleClose:()=>{p(!1),g(null)},data:m})]})}}}]);