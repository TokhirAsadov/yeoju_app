/*! For license information please see 4973.7a6c9e5e.js.LICENSE.txt */
(self.webpackChunkkiut_client=self.webpackChunkkiut_client||[]).push([["4973"],{47942:function(n,e,l){"use strict";l.r(e);var t=l("58865");l("33948"),l("31672"),l("59461"),l("2490"),l("57640"),l("9924");var i=l("85893"),a=l("67294"),r=l("71893"),d=l("16393"),o=l("50533"),u=l("27233"),s=l("57024"),c=l("79352"),f=l("39711"),p=l("55693"),x=l("42154"),v=l("32392"),m=l("44025"),h=l("61261"),g=l("27120"),j=l("92120"),b=l("71096");function w(){let n=(0,t._)(["\n    width: 100%;\n    display: flex;\n    gap: 10px;\n    align-items: center;\n    font-size: 16px;\n    text-align: start;\n    color: ",";\n    font-weight: bold;\n    ","\n"]);return w=function(){return n},n}function C(){let n=(0,t._)(["\n    padding: 15px;\n    display: flex;\n    flex-direction: column;\n    gap: 10px;\n    background-color: #fff;\n    border-radius: 0.5rem;\n    box-shadow: rgba(0, 0, 0, 0.05) 0px 1px 2px 0px;\n\n    p {\n        display: flex;\n        align-items: center;\n        color: black;\n        font-size: 14px;\n    }\n\n    &:hover {\n        cursor: pointer;\n        box-shadow: 1px 3px 8px 0px rgba(34, 60, 80, 0.2);\n    }\n"]);return C=function(){return n},n}function k(){let n=(0,t._)(["\n    width: 100%;\n    display: grid;\n    grid-template-columns: 1fr 1fr 1fr 1fr;\n    gap: 20px;\n    ","\n    ","\n    ","\n    ","\n    ","\n"]);return k=function(){return n},n}function S(){let n=(0,t._)(["\n    width: 100%;\n    font-size: 25px;\n    font-weight: bold;\n    ","\n"]);return S=function(){return n},n}function y(){let n=(0,t._)(["\n    padding: 1rem !important;\n    color: ",";\n"]);return y=function(){return n},n}let _=r.default.div(w(),d.mainColor,(0,s.extrasmall)({fontSize:"14px"})),F=r.default.div(C()),I=r.default.div(k(),(0,s.xlarge)({gridTemplateColumns:"1fr 1fr 1fr  "}),(0,s.large)({gridTemplateColumns:"1fr 1fr 1fr  "}),(0,s.medium)({gridTemplateColumns:"1fr 1fr "}),(0,s.small)({gridTemplateColumns:"1fr 1fr "}),(0,s.extrasmall)({gridTemplateColumns:"1fr "})),N=r.default.p(S(),(0,s.extrasmall)({fontSize:"20px"})),Y=r.default.div(y(),d.mainColor);e.default=()=>{let n=(0,f.useNavigate)(),e=(0,o.useSelector)(n=>{var e;return null==n?void 0:(e=n.student)===null||void 0===e?void 0:e.student}),{headers:l}=(0,d.getHeaders)(),[t,r]=(0,a.useState)([]),s=(0,o.useSelector)(n=>{var e;return null==n?void 0:(e=n.educationYear)===null||void 0===e?void 0:e.educationYear})||null,[w,C]=(0,a.useState)([]),k=(0,o.useDispatch)(),S=async()=>{await u.default.get(d.BASE_URL+"/education/educationYearsForSelected",{headers:l}).then(n=>{var e,l,t;C(null==n?void 0:(e=n.data)===null||void 0===e?void 0:e.obj),k((0,j.fetchEducationYear)(null==n?void 0:(t=n.data)===null||void 0===t?void 0:(l=t.obj)===null||void 0===l?void 0:l[0]))}).catch(n=>{})},y=async n=>{await u.default.get(d.BASE_URL+"/groupConnect/getLessonForGroup/".concat(null==s?void 0:s.id,"?groupName=").concat(n),{headers:l}).then(n=>{r(null==n?void 0:n.data)}).catch(n=>{})};(0,a.useEffect)(()=>{var n;S(),y(null==e?void 0:(n=e.groupData)===null||void 0===n?void 0:n.name)},[]);let z=e=>{let l={lessonName:e.lessonName,educationYearId:s.id,lessonId:e.lessonId,teacherId:e.teacherId};k((0,b.setInfoStudentForLesson)(l)),n("".concat(e.lessonName))};return(0,i.jsxs)(Y,{children:[(0,i.jsxs)(p.default,{sx:{display:"flex",justifyContent:"space-between",padding:"10px"},children:[(0,i.jsx)(N,{children:"Subjects"}),(0,i.jsx)(p.default,{sx:{minWidth:150,background:"#fff"},children:(0,i.jsxs)(x.default,{fullWidth:!0,children:[(0,i.jsx)(v.default,{htmlFor:"semestr",children:"O'quv yilli"}),(0,i.jsx)(m.default,{labelId:"semestr",id:"demo-simple-select",value:null==s?void 0:s.name,onChange:n=>{n.target.value!==(null==s?void 0:s.name)&&k((0,g.educationYearStatisticsFetching)()),k((0,j.fetchEducationYear)(null==w?void 0:w.find(e=>(null==e?void 0:e.name)===n.target.value)))},label:"O'quv yili",children:null==w?void 0:w.map((n,e)=>(0,i.jsx)(h.default,{value:null==n?void 0:n.name,children:null==n?void 0:n.name},null==n?void 0:n.id))})]})})]}),(0,i.jsx)(I,{children:null==t?void 0:t.map((n,e)=>(0,i.jsxs)(F,{onClick:()=>z(n),children:[(0,i.jsxs)(_,{children:[(0,i.jsx)(p.default,{sx:{w:"30px",h:"30px"},children:(0,i.jsx)(c.RiBookMarkFill,{size:30})}),(0,i.jsx)("span",{children:null==n?void 0:n.lessonName})]}),(0,i.jsxs)("p",{children:["Teacher : ",(0,i.jsx)(i.Fragment,{children:null==n?void 0:n.fullName})]})]},e))})]})}}}]);