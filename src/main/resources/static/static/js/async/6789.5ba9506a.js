/*! For license information please see 6789.5ba9506a.js.LICENSE.txt */
(self.webpackChunkkiut_client=self.webpackChunkkiut_client||[]).push([["6789"],{17171:function(e,l,a){"use strict";var n=a("5751");Object.defineProperty(l,"__esModule",{value:!0}),l.default=void 0;var d=n(a("64938")),t=a("85893"),i=(0,d.default)((0,t.jsx)("path",{d:"M19 3H5c-1.11 0-2 .9-2 2v14c0 1.1.89 2 2 2h14c1.11 0 2-.9 2-2V5c0-1.1-.89-2-2-2zm-9 14-5-5 1.41-1.41L10 14.17l7.59-7.59L19 8l-9 9z"}),"CheckBox");l.default=i},39628:function(e,l,a){"use strict";var n=a("5751");Object.defineProperty(l,"__esModule",{value:!0}),l.default=void 0;var d=n(a("64938")),t=a("85893"),i=(0,d.default)((0,t.jsx)("path",{d:"M19 5v14H5V5h14m0-2H5c-1.1 0-2 .9-2 2v14c0 1.1.9 2 2 2h14c1.1 0 2-.9 2-2V5c0-1.1-.9-2-2-2z"}),"CheckBoxOutlineBlank");l.default=i},67637:function(e,l,a){"use strict";a.r(l);var n=a("58865");a("33948"),a("31672"),a("59461"),a("2490"),a("57640"),a("9924");var d=a("85893"),t=a("67294"),i=a.n(t),o=a("50533"),u=a("27233"),r=a("16393"),s=a("29258"),c=a("71893"),h=a("7104"),m=a("55693"),f=a("25727"),v=a("89589"),p=a("74113"),x=a("94718"),j=a("97367"),g=a("80471"),b=a("42154"),y=a("32392"),C=a("44025"),S=a("61261"),I=a("15352"),w=a("52861"),k=a("60155"),T=a("39628"),L=a("57024"),A=a("17171"),E=a("72132"),_=a("39711"),B=a("5434"),N=a("27120"),R=a("92120");function U(){let e=(0,n._)(["\n  ","\n"]);return U=function(){return e},e}function z(){let e=(0,n._)(["\n  padding: 0 10px;\n  display: flex;\n  align-items: center;\n  justify-content: space-between;\n  gap: 20px;\n  flex-wrap: wrap;\n\n  p {\n    color: ",";\n    font-size: 30px;\n    font-weight: bold;\n    ","\n  }\n  ","\n"]);return z=function(){return e},e}function O(){let e=(0,n._)(["\n  display: grid;\n  grid-template-columns: 1fr 1fr 1fr;\n  gap: 20px;\n  padding: 10px;\n  ","\n  ","\n  ","\n  ","\n  ",""]);return O=function(){return e},e}function M(){let e=(0,n._)(["\n  width: 100%;\n  padding: 1rem;\n"]);return M=function(){return e},e}let Y={position:"absolute",top:"50%",left:"50%",transform:"translate(-50%, -50%)",width:500,bgcolor:"background.paper",boxShadow:24,borderRadius:2,p:3},F=c.default.div(U(),(0,L.extrasmall)({width:"95% !important"})),H=c.default.h1(z(),r.mainColor,(0,L.extrasmall)({textAlign:"center",fontSize:"25px"}),(0,L.extrasmall)({justifyContent:"center"})),K=c.default.div(O(),(0,L.xlarge)({gridTemplateColumns:"1fr 1fr 1fr  "}),(0,L.large)({gridTemplateColumns:"1fr 1fr 1fr  "}),(0,L.medium)({gridTemplateColumns:"1fr 1fr "}),(0,L.small)({gridTemplateColumns:"1fr 1fr "}),(0,L.extrasmall)({gridTemplateColumns:"1fr "})),G=c.default.div(M());l.default=()=>{let e=["UZBEK","ENGLISH","RUSSIAN"],l=["KUNDUZGI","KECHKI","SIRTQI","MAGISTRATURE"],[a,n]=(0,t.useState)([]),c=[1,2,3,4,5,6],[L,U]=(0,t.useState)([]),[z,O]=(0,t.useState)(!1),[M,P]=(0,t.useState)(!0),[V,D]=(0,t.useState)([]),W=(0,o.useSelector)(e=>{var l;return null==e?void 0:null===(l=e.educationYear)||void 0===l?void 0:l.educationYear})||null,[Z,q]=(0,t.useState)(null),Q=(0,o.useDispatch)(),{headers:J}=(0,r.getHeaders)(),[X,$]=(0,t.useState)(!0),[ee,el]=(0,t.useState)([]),ea={id:null,eduLang:e[0],eduType:l[0],educationYearId:null==W?void 0:W.id,subjectId:null,teacherId:null,level:c[0],groupsIds:[]},[en,ed]=(0,t.useState)(ea),et=()=>{O(!1),ed(ea)},ei=async()=>{await u.default.get(r.BASE_URL+"/education/educationYearsForSelected",{headers:J}).then(e=>{var l,a,n,d,t;D(null==e?void 0:null===(l=e.data)||void 0===l?void 0:l.obj),q(null==e?void 0:null===(n=e.data)||void 0===n?void 0:null===(a=n.obj)||void 0===a?void 0:a[0]),Q((0,R.fetchEducationYear)(null==e?void 0:null===(t=e.data)||void 0===t?void 0:null===(d=t.obj)||void 0===d?void 0:d[0]))}).catch(e=>{})},eo=()=>{P(!0),U([]),u.default.get("".concat(r.BASE_URL,"/plan/getTeacherWIthSubjectForPlan/").concat(null==W?void 0:W.id),{headers:J}).then(e=>{var l,a;U(null==e?void 0:null===(a=e.data)||void 0===a?void 0:null===(l=a.obj)||void 0===l?void 0:l.sort((e,l)=>e.fullName>l.fullName?1:-1)),P(!1)}).catch(e=>Q((0,s.kafedraTeacherStatisticsFetchingError)(e)))},eu=async e=>{u.default.post("".concat(r.BASE_URL,"/plan/createdPlanByKafedraMudiri"),e,{headers:J}).then(e=>{E.toast.success("created plan of subject successfully"),et()}).catch(e=>{})},er=async()=>{await u.default.get("".concat(r.BASE_URL,"/lesson/getAllLessonByKafedraOwner"),{headers:J}).then(e=>{var l;n(null===(l=e.data)||void 0===l?void 0:l.obj)}).catch(e=>{})},es=async()=>{await u.default.get("".concat(r.BASE_URL,"/group/getGroupsForKafedraMudiri?lang=").concat(null==en?void 0:en.eduLang,"&eduType=").concat(null==en?void 0:en.eduType,"&level=").concat(null==en?void 0:en.level),{headers:J}).then(e=>{var l;el(null==e?void 0:null===(l=e.data)||void 0===l?void 0:l.obj)}).catch(e=>{})};(0,t.useEffect)(()=>{es(),""!==en.teacherId&&en.groupsIds.length>0&&""!==en.subjectId&&""!=en.level?$(!1):$(!0)},[en]),(0,t.useEffect)(()=>{W&&eo(),ei(),er()},[]),(0,t.useEffect)(()=>{(null==V?void 0:V.length)>0&&q(V[0])},[V]);function ec(e){let{loading:l=!1}=e;return(0,d.jsxs)(h.Card,{children:[(0,d.jsx)(h.CardHeader,{avatar:l&&(0,d.jsx)(h.Skeleton,{animation:"wave",variant:"circular",width:40,height:40}),action:l&&null,title:l&&(0,d.jsx)(h.Skeleton,{animation:"wave",height:15,width:"80%",style:{marginBottom:6}}),subheader:l&&(0,d.jsx)(h.Skeleton,{animation:"wave",height:15,width:"40%"})}),(0,d.jsx)(h.CardContent,{children:l&&(0,d.jsxs)(i().Fragment,{children:[(0,d.jsx)(h.Skeleton,{animation:"wave",height:15,style:{marginBottom:6}}),(0,d.jsx)(h.Skeleton,{animation:"wave",height:15})]})})]})}return(0,d.jsxs)(G,{children:[(0,d.jsxs)(H,{children:[(0,d.jsx)("p",{children:"Connect group to teacher"}),(0,d.jsx)(x.default,{variant:"contained",endIcon:(0,d.jsx)(B.MdOutlineCreateNewFolder,{}),onClick:()=>{es(),O(!0)},children:"create group plan"})]}),(0,d.jsxs)(b.default,{sx:{width:"250px",margin:"25px 0"},children:[(0,d.jsx)(y.default,{id:"demo-simple-select-readonly-label15",children:"Academic year"}),(0,d.jsx)(C.default,{labelId:"demo-simple-select-readonly-label15",id:"demo-simple-select-readonly15",value:(null==Z?void 0:Z.name)||"",onChange:e=>{q(V.find(l=>(null==l?void 0:l.name)===e.target.value))},label:"Academic year",children:null==V?void 0:V.map(e=>(0,d.jsx)(S.default,{value:null==e?void 0:e.name,children:null==e?void 0:e.name},null==e?void 0:e.id))})]}),(0,d.jsx)(K,{children:M?Array.from({length:4}).map((e,l)=>(0,d.jsx)(ec,{loading:!0},l)):L.map((e,l)=>{var a,n;return(0,d.jsx)(h.Card,{children:(0,d.jsxs)(h.CardContent,{children:[(0,d.jsxs)(m.default,{display:"flex",gap:2,alignItems:"center",children:[(0,d.jsx)(f.default,{alt:null==e?void 0:e.fullName,src:(null==e?void 0:e.photo)?r.BASE_URL+"/attachment/download/"+(null==e?void 0:null===(a=e.photo)||void 0===a?void 0:a.id):""}),(0,d.jsx)(v.default,{children:null==e?void 0:e.fullName})]}),(0,d.jsx)(m.default,{mt:2,children:(0,d.jsx)("ul",{style:{listStyleType:"none",color:"".concat(r.mainColor),fontWeight:"bold",fontSize:"16px"},children:null==e?void 0:null===(n=e.subjects)||void 0===n?void 0:n.map((e,l)=>(0,d.jsx)("li",{children:(0,d.jsx)(_.Link,{to:null==e?void 0:e.name,children:null==e?void 0:e.name})},l))})})]})},l)})}),(0,d.jsx)(j.default,{open:z,onClose:et,children:(0,d.jsxs)(m.default,{sx:Y,component:F,children:[(0,d.jsxs)(m.default,{display:"flex",justifyContent:"space-between",alignItems:"center",children:[(0,d.jsx)(v.default,{id:"modal-modal-title",variant:"h6",component:"h2",children:"Create group plan"}),(0,d.jsx)(p.default,{onClick:et,"aria-label":"close",size:"medium",children:(0,d.jsx)(g.CgClose,{})})]}),(0,d.jsxs)(b.default,{sx:{width:"100%",margin:"25px 0"},children:[(0,d.jsx)(y.default,{id:"demo-simple-select-readonly-label",children:"Academic year"}),(0,d.jsx)(C.default,{labelId:"demo-simple-select-readonly-label",id:"demo-simple-select-readonly",value:null==W?void 0:W.name,onChange:e=>{e.target.value!==(null==W?void 0:W.name)&&Q((0,N.educationYearStatisticsFetching)()),Q((0,R.fetchEducationYear)(null==V?void 0:V.find(l=>(null==l?void 0:l.name)===e.target.value)))},label:"Academic year",children:null==V?void 0:V.map(e=>(0,d.jsx)(S.default,{value:null==e?void 0:e.name,children:null==e?void 0:e.name},null==e?void 0:e.id))})]}),(0,d.jsxs)(m.default,{sx:{display:"flex",gap:"20px"},children:[(0,d.jsxs)(b.default,{sx:{width:"100%"},children:[(0,d.jsx)(y.default,{id:"demo-simple-select-readonly-label1",children:"Language"}),(0,d.jsx)(C.default,{labelId:"demo-simple-select-readonly-label1",id:"demo-simple-select-readonly",value:en.eduLang,label:"Language",onChange:e=>ed(l=>({...l,eduLanguage:e.target.value})),children:e.map(e=>(0,d.jsx)(S.default,{value:e,children:e},e))})]}),(0,d.jsxs)(b.default,{sx:{width:"100%"},children:[(0,d.jsx)(y.default,{id:"demo-simple-select-readonly-label1",children:"Type of education"}),(0,d.jsx)(C.default,{labelId:"demo-simple-select-readonly-label1",id:"demo-simple-select-readonly",value:en.eduType,label:"Type of education",onChange:e=>ed(l=>({...l,eduType:e.target.value})),children:l.map(e=>(0,d.jsx)(S.default,{value:e,children:e},e))})]}),(0,d.jsxs)(b.default,{sx:{width:"100%"},children:[(0,d.jsx)(y.default,{id:"demo-simple-select-readonly-label1",children:"\u0421ourse"}),(0,d.jsx)(C.default,{labelId:"demo-simple-select-readonly-label1",id:"demo-simple-select-readonly",value:en.level,label:"\u0421ourse",onChange:e=>ed(l=>({...l,level:e.target.value})),children:c.map(e=>(0,d.jsx)(S.default,{value:e,children:e},e))})]})]}),(0,d.jsxs)(b.default,{sx:{width:"100%",margin:"25px 0"},children:[(0,d.jsx)(y.default,{id:"demo-simple-select-readonly-label1",children:"Teacher"}),(0,d.jsx)(C.default,{labelId:"demo-simple-select-readonly-label1",id:"demo-simple-select-readonly",value:en.teacherId,label:"Teacher",onChange:e=>ed(l=>({...l,teacherId:e.target.value})),children:null==L?void 0:L.map((e,l)=>(0,d.jsx)(S.default,{value:null==e?void 0:e.id,children:l+1+". "+(null==e?void 0:e.fullName)},l))})]}),(0,d.jsxs)(b.default,{sx:{width:"100%"},children:[(0,d.jsx)(y.default,{id:"demo-simple-select-readonly-label1",children:"Subject"}),(0,d.jsx)(C.default,{labelId:"demo-simple-select-readonly-label1",id:"demo-simple-select-readonly",value:en.subjectId,label:"Subject",onChange:e=>ed(l=>({...l,subjectId:e.target.value})),children:null==a?void 0:a.map((e,l)=>(0,d.jsx)(S.default,{value:null==e?void 0:e.subjectId,children:l+1+". "+(null==e?void 0:e.subjectName)},l))})]}),(0,d.jsx)(h.Autocomplete,{multiple:!0,id:"checkboxes-tags-demo",options:ee,disableCloseOnSelect:!0,getOptionLabel:e=>e.groupName,value:en.groupsIds,onChange:(e,l)=>{ed(e=>({...e,groupsIds:l}))},isOptionEqualToValue:(e,l)=>e.id===l.id,renderOption:(e,l,a)=>{let{selected:n}=a;return(0,d.jsxs)("li",{...e,children:[(0,d.jsx)(I.default,{icon:(0,d.jsx)(T.default,{fontSize:"small"}),checkedIcon:(0,d.jsx)(A.default,{fontSize:"small"}),style:{marginRight:8},checked:n}),null==l?void 0:l.groupName]})},style:{width:"100%",margin:"25px 0"},renderInput:e=>(0,d.jsx)(w.default,{...e,label:"Groups"})}),(0,d.jsx)(m.default,{mt:2,display:"flex",justifyContent:"end",children:(0,d.jsx)(x.default,{onClick:()=>{var e;eu({...en,educationYearId:null==W?void 0:W.id,groupsIds:(null==en?void 0:null===(e=en.groupsIds)||void 0===e?void 0:e.map(e=>e.id))||[]})},variant:"contained",disabled:X,endIcon:(0,d.jsx)(k.IoSend,{}),children:" save"})})]})})]})}}}]);