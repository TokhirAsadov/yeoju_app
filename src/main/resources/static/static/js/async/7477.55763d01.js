/*! For license information please see 7477.55763d01.js.LICENSE.txt */
(self.webpackChunkkiut_client=self.webpackChunkkiut_client||[]).push([["7477"],{12993:function(e,t,l){"use strict";l.r(t);var a=l("58865");l("57640"),l("9924"),l("33948");var n=l("85893"),i=l("67294"),d=l("71893"),s=l("16393"),r=l("8193"),u=l("7104"),c=l("55693"),o=l("42154"),h=l("44025"),f=l("61261"),m=l("16356"),x=l("27233"),g=l("50533"),p=l("32392"),j=l("72132"),v=l("94718"),S=l("47516"),C=l("39711"),A=l("57024");function b(){let e=(0,a._)(["\n    display: flex;\n    align-items: center;\n    justify-content: space-between;\n\n    p {\n        color: ",";\n        font-size: 30px;\n        font-weight: bold;\n        ","\n    }\n"]);return b=function(){return e},e}function E(){let e=(0,a._)(["\n    width: 100%;\n    padding: 1rem;\n\n    .MuiDataGrid-columnHeaderTitleContainer {\n        justify-content: center;\n    }\n"]);return E=function(){return e},e}let I=d.default.h1(b(),s.mainColor,(0,A.extrasmall)({textAlign:"center",fontSize:"20px"})),N=d.default.div(E());t.default=function(){var e;let t=["TEACHING","FINISHED","ACADEMIC_VACATION","EXPELLED_FROM_UNIVERSITY","ACADEMIC_DEBTOR","TRANSFER"],{headers:l}=(0,s.getHeaders)(),a=[{field:"count",headerName:"\u2116",width:40,editable:!1,justifyContent:"center",align:"center"},{field:"fullName",headerName:"Full Name",flex:1,minWidth:400,editable:!1},{field:"groupName",headerName:"Group",flex:1,minWidth:100,editable:!1,justifyContent:"center",align:"center"},{field:"passport",headerName:"Passport",flex:1,minWidth:150,justifyContent:"center",align:"center",editable:!1},{field:"login",headerName:"Login",flex:.8,minWidth:80,justifyContent:"center",align:"center",editable:!1},{field:"rfid",headerName:"RFID",flex:.8,minWidth:140,justifyContent:"center",align:"center",editable:!1},{field:"rektororder",headerName:"Rektors order",flex:1,minWidth:150,justifyContent:"center",align:"center",editable:!1},{field:"changeStatus",headerName:"Change Status",flex:1,minWidth:180,editable:!1,justifyContent:"center",align:"center",renderCell:e=>(0,n.jsx)(c.default,{sx:{width:"180px"},children:(0,n.jsx)(o.default,{fullWidth:!0,children:(0,n.jsx)(h.default,{size:"small",labelId:"demo-simple-select-label",id:"demo-simple-select",onChange:t=>k(t,e),children:t.map(e=>(0,n.jsx)(f.default,{value:e,children:e},e))})})})},{field:"teachStatus",headerName:"Status",flex:1,minWidth:180,justifyContent:"center",align:"center",editable:!1}],d=(0,g.useSelector)(e=>{var t;return null==e?void 0:null===(t=e.dekanat)||void 0===t?void 0:t.dekanat})||null,[A,b]=(0,i.useState)([]),[E,y]=(0,i.useState)(""),[D,R]=(0,i.useState)(""),_=(0,C.useNavigate)();function T(){""===D&&""===E?x.default.get(s.BASE_URL+"/student/getAllStudentDataForDean",{headers:l}).then(e=>{var t;b(null===(t=e.data)||void 0===t?void 0:t.map((e,t)=>({...e,count:t+1})))}).catch(e=>{}):""!==D&&""===E?x.default.get(s.BASE_URL+"/student/getStudentDataForTeachStatusAllByFacultyId?facultyId=".concat(D),{headers:l}).then(e=>{var t;b(null===(t=e.data)||void 0===t?void 0:t.map((e,t)=>({...e,count:t+1})))}).catch(e=>{}):""!==E&&""!==D&&x.default.get(s.BASE_URL+"/student/getStudentDataForTeachStatusAndFacultyId?teachStatus=".concat(E,"&facultyId=").concat(D),{headers:l}).then(e=>{var t;b(null===(t=e.data)||void 0===t?void 0:t.map((e,t)=>({...e,count:t+1})))}).catch(e=>{})}(0,i.useEffect)(()=>{T()},[E,D]);let k=(e,t)=>{let l=e.target.value,{id:a}=t;x.default.get(s.BASE_URL+"/student/changeTeachStatusOfStudent/".concat(a,"?teachStatus=").concat(l)).then(e=>{T(),j.toast.success("Success change status")}).catch(e=>{j.toast.error("Error change status")})};return(0,n.jsxs)(N,{children:[(0,n.jsxs)(I,{children:[(0,n.jsxs)("p",{children:[(0,n.jsx)(r.AiOutlineUnorderedList,{})," Archive"]}),(0,n.jsx)(v.default,{onClick:()=>_(-1),variant:"outlined",startIcon:(0,n.jsx)(S.BiArrowBack,{}),children:"Back"})]}),(0,n.jsxs)(c.default,{sx:{mt:"15px",display:"flex",gap:"20px"},children:[(0,n.jsx)(c.default,{sx:{width:150},children:(0,n.jsxs)(o.default,{fullWidth:!0,children:[(0,n.jsx)(p.default,{id:"demo-simple-select-label",children:"Yo'nalish"}),(0,n.jsxs)(h.default,{labelId:"demo-simple-select-label",id:"demo-simple-select",value:D,label:"Yo'nalish",onChange:e=>{R(e.target.value)},children:[(0,n.jsx)(f.default,{value:"",children:(0,n.jsx)("em",{children:"All"})}),null==d?void 0:null===(e=d.faculties)||void 0===e?void 0:e.map(e=>(0,n.jsx)(f.default,{value:null==e?void 0:e.id,children:null==e?void 0:e.shortName},null==e?void 0:e.id))]})]})}),(0,n.jsx)(c.default,{sx:{width:150},children:(0,n.jsxs)(o.default,{fullWidth:!0,children:[(0,n.jsx)(p.default,{id:"demo-simple-select-label",children:"Status"}),(0,n.jsxs)(h.default,{labelId:"demo-simple-select-label",id:"demo-simple-select",value:E,label:"Status",onChange:e=>{y(e.target.value)},children:[(0,n.jsx)(f.default,{value:"",children:(0,n.jsx)("em",{children:"All"})}),["FINISHED","ACADEMIC_VACATION","EXPELLED_FROM_UNIVERSITY","ACADEMIC_DEBTOR","TRANSFER"].map(e=>(0,n.jsx)(f.default,{value:e,children:e},e))]})]})})]}),(0,n.jsx)(u.Card,{sx:{mt:3},children:(0,n.jsx)(u.CardContent,{children:(0,n.jsx)(m.DataGrid,{style:{width:"100%",minHeight:"300px!important"},columns:a,rows:A,components:{Toolbar:m.GridToolbar},autoHeight:!0,pageSize:20,initialState:{...A.initialState,columns:{columnVisibilityModel:{id:!1,cardNo:!1,login:!1,email:!1,passport:!1,rektororder:!1,rfid:!1,changeStatus:!1}}}})})})]})}}}]);