/*! For license information please see 193.23aabc9e.js.LICENSE.txt */
(self.webpackChunkkiut_client=self.webpackChunkkiut_client||[]).push([["193"],{73598:function(e,n,t){"use strict";t.r(n),t("33948");var i=t("85893"),l=t("67294"),a=t("60491"),s=t("27233"),d=t("16393"),r=t("50533");n.default=(0,l.memo)(()=>{let e=(0,r.useSelector)(e=>{var n;return null==e?void 0:null===(n=e.student)||void 0===n?void 0:n.student})||null,[n,t]=(0,l.useState)(null),o=()=>{s.default.get(d.BASE_URL+"/notificationOuter/getStudentOuterNotifications/".concat(e.id)).then(e=>{var n;t(e.data.obj[(null===(n=e.data.obj)||void 0===n?void 0:n.length)-1])}).catch(e=>{})};return(0,l.useEffect)(()=>{o()},[]),(0,i.jsx)(i.Fragment,{children:(0,i.jsx)(a.default,{data:n,studentData:e})})})},74472:function(e,n,t){"use strict";t.r(n);var i=t("58865");t("33948");var l=t("85893"),a=t("67294"),s=t("89258"),d=t("30381"),r=t.n(d),o=t("49492"),c=t("7701"),u=t("94718"),f=t("5434"),h=t("84059"),x=t("71893");function p(){let e=(0,i._)(["\n    width: 640px;\n    margin: 0 auto;\n    font-size: 15px;\n    padding: 10px;\n    color: black;\n    line-height: 1.5;\n\n    p{\n        margin: 0;\n        padding:0;\n    }\n    .header {\n        display: flex;\n        align-items: center;\n        justify-content: center;\n        border-bottom: 1px solid black;\n    }\n\n    .head_title {\n        font-size: 18px;\n    }\n\n    .date {\n        padding: 0 25px;\n        margin: 10px 0;\n        display: flex;\n        justify-content: space-between;\n        align-items: center;\n    }\n\n    .date_text {\n        font-size: 12px;\n    }\n\n\n    .app_number {\n        padding: 0 25px;\n        margin: 0;\n        display: flex;\n        justify-content: flex-start;\n    }\n\n    .core_title {\n        text-align: center;\n        margin-top: 20px;\n        font-weight: 500;\n    }\n\n    .item {\n        margin-top: 15px;\n        padding-left: 20px;\n        display: flex;\n        justify-content: flex-start;\n        align-items: center;\n    }\n\n    .info {\n        width: 40%;\n    }\n\n    .info2 {\n        width: 60%;\n    }\n\n    .info_text {\n        margin-top: 15px;\n        padding: 0 20px;\n        line-height: 1.5;\n        text-align: justify;\n    }\n\n    .given_text {\n        padding-bottom: 20px;\n        margin-top: 20px;\n        margin-bottom: 10px;\n        border-bottom: 1px solid #000;\n    }\n\n    .footer {\n        margin-top: 25px;\n        display: flex;\n        align-items: center;\n        justify-content: flex-start;\n    }\n\n    .flex_footer {\n        display: flex;\n        align-items: center;\n        justify-content: space-between;\n    }\n    \n\n    .qr_code_box {\n        margin-top: 25px;\n    }\n\n"]);return p=function(){return e},e}let m=x.default.div(p());n.default=e=>{let{contentPdf:n}=e,[t,i]=(0,a.useState)(!1),d=(0,a.useRef)(null),x=window.location.origin+"/file/services/reference/"+(null==n?void 0:n.id),p=async()=>{i(!0);let e=d.current,n={margin:10,filename:"certificateOfStudentStatus".concat(new Date().getTime(),".pdf"),image:{type:"jpeg",quality:1},html2canvas:{scale:2},jsPDF:{unit:"mm",format:"a4",orientation:"portrait"}};await (0,o.default)().set(n).from(e).save(),i(!1)};return(0,l.jsxs)(l.Fragment,{children:[t?(0,l.jsx)(u.default,{size:"small",variant:"outlined",endIcon:(0,l.jsx)(c.default,{size:18}),children:"download"}):(0,l.jsx)(u.default,{size:"small",variant:"contained",endIcon:(0,l.jsx)(f.MdOutlineFileDownload,{size:22}),onClick:p,children:"download"}),(0,l.jsx)("div",{style:{display:"none"},children:(0,l.jsxs)(m,{ref:d,children:[(0,l.jsxs)("div",{className:"header",children:[(0,l.jsx)("div",{children:(0,l.jsx)("img",{width:140,src:s,alt:"logo"})}),(0,l.jsxs)("div",{children:[(0,l.jsxs)("b",{className:"head_title",children:["TOSHKENT KIMYO XALQARO UNIVERSITETI ",(0,l.jsx)("br",{}),"KIMYO INTERNATIONAL UNIVERSITY IN TASHKENT"]}),(0,l.jsx)("br",{})," ",(0,l.jsx)("br",{}),(0,l.jsx)("i",{style:{fontSize:"14px"},children:"100121, Toshkent shahri, Yakkasaroy tumani, Usmon Nosir ko`chasi, 156 uy. Tel.: +998781294040. Veb-sayt: www.kiut.uz. Elektron pochta: info@kiut.uz"})]})]}),(0,l.jsxs)("div",{className:"date",children:[(0,l.jsxs)("p",{children:["\u2116:",null==n?void 0:n.id]}),(0,l.jsx)("p",{children:r()(null==n?void 0:n.time).format("DD.MM.YYYY")})]}),(0,l.jsx)("div",{className:"app_number",children:(0,l.jsxs)("p",{children:["Application number: ",null==n?void 0:n.numeration]})}),(0,l.jsx)("p",{className:"core_title",children:"CERTIFICATE OF STUDENT STATUS"}),(0,l.jsxs)("div",{className:"item",children:[(0,l.jsx)("p",{className:"info",children:"Full Name:"}),(0,l.jsx)("p",{className:"info2",children:null==n?void 0:n.fullName})]}),(0,l.jsxs)("div",{className:"item",children:[(0,l.jsx)("p",{className:"info",children:"Passport Number:"}),(0,l.jsx)("p",{className:"info2",children:null==n?void 0:n.passport})]}),(0,l.jsxs)("div",{className:"item",children:[(0,l.jsx)("p",{className:"info",children:"Major:"}),(0,l.jsx)("p",{className:"info2",children:null==n?void 0:n.direction})]}),(0,l.jsxs)("div",{className:"item",children:[(0,l.jsx)("p",{className:"info",children:"Grade:"}),(0,l.jsx)("p",{className:"info2",children:null==n?void 0:n.grade})]}),(0,l.jsxs)("div",{className:"item",children:[(0,l.jsx)("p",{className:"info",children:"Language of Instruction:"}),(0,l.jsx)("p",{className:"info2",children:null==n?void 0:n.eduLang})]}),(0,l.jsxs)("div",{className:"item",children:[(0,l.jsx)("p",{className:"info",children:"Mode of Study:"}),(0,l.jsx)("p",{className:"info2",children:null==n?void 0:n.eduType})]}),(0,l.jsxs)("div",{className:"item",children:[(0,l.jsx)("p",{className:"info",children:"Length of Study:"}),(0,l.jsx)("p",{className:"info2",children:null==n?void 0:n.lengthOfStudying})]}),(0,l.jsxs)("div",{className:"item",children:[(0,l.jsx)("p",{className:"info",children:"Remark: Rector\u2019s order:"}),(0,l.jsx)("p",{className:"info2",children:null==n?void 0:n.rektororder})]}),(0,l.jsx)("p",{className:"info_text",children:"Additional information: Kimyo international University in Tashkent provides educational services in the field of higher education based on state license No044527 dated on 21.10.2022 issued by the State Inspectorate for Supervision of Quality in Education under the Cabinet of Ministers of the Republic of Uzbekistan and the Decree of the Cabinet Ministers of the Republic of Uzbekistan No.1007 dated on 17.12.2019."}),(0,l.jsx)("p",{className:"given_text",children:"This Certificate of student status is given to:"}),(0,l.jsxs)("div",{className:"flex_footer",children:[(0,l.jsx)("div",{className:"footer",children:(0,l.jsxs)("p",{children:["Course leader:  ",null==n?void 0:n.dean]})}),(0,l.jsx)("div",{className:"qr_code_box",children:(0,l.jsx)(h.default,{value:x,size:120})})]})]})})]})}},94560:function(e,n,t){"use strict";t.r(n);var i=t("58865");t("33948");var l=t("85893"),a=t("67294"),s=t("27233"),d=t("16393"),r=t("74472"),o=t("71893");function c(){let e=(0,i._)(["\n\n"]);return c=function(){return e},e}let u=o.default.div(c());n.default=(0,a.memo)(function(e){let{id:n}=e,[t,i]=(0,a.useState)(null);return(0,a.useEffect)(()=>{s.default.get(d.BASE_URL+"/student/getDataForStudentReference2/".concat(n)).then(e=>{i(e.data.obj)}).catch(e=>{})},[]),(0,l.jsx)(u,{children:t&&(0,l.jsx)(r.default,{contentPdf:t})})})},5376:function(e,n,t){"use strict";t.r(n);var i=t("58865");t("33948"),t("43847"),t("57640"),t("9924"),t("64211"),t("41874"),t("2490");var l=t("85893"),a=t("67294"),s=t("71893"),d=t("7104"),r=t("16393"),o=t("94718"),c=t("5434"),u=t("89589"),f=t("55693"),h=t("97367"),x=t("74113"),p=t("80471"),m=t("32392"),j=t("61261"),v=t("42154"),g=t("44025"),y=t("52861"),b=t("63750"),N=t("27233"),S=t("50533"),w=t("92120"),C=t("72132"),I=t("30381"),k=t.n(I),_=t("94560"),E=t("78951"),T=t("73598");function R(){let e=(0,i._)(["\n    margin-top: 25px;\n    width: 100%;\n    overflow-x: scroll;\n\n    table {\n        min-width: 700px;\n        border-collapse: collapse;\n        width: 100%;\n        border-radius: 5px;\n        overflow: hidden;\n\n        td, th {\n            border: 1px solid #ddd;\n            padding: 8px;\n            font-size: 15px;\n        }\n\n        th {\n            text-align: center;\n        }\n\n        tr {\n            &:nth-child(even) {\n                background-color: #fcf9f9;\n            }\n        }\n\n        th {\n            background-color: ",";\n            color: white;\n        }\n    }\n\n"]);return R=function(){return e},e}function A(){let e=(0,i._)(["\n    display: flex;\n    justify-content: space-between;\n    gap: 10px;\n    align-items: center;\n"]);return A=function(){return e},e}let D=s.default.div(R(),r.mainColor),O=s.default.div(A()),Y={position:"absolute",top:"50%",left:"50%",transform:"translate(-50%, -50%)",width:400,bgcolor:"#fff",boxShadow:24,p:3,borderRadius:1};n.default=()=>{let[e,n]=(0,a.useState)(!1),[t,i]=(0,a.useState)({studentId:null,deanId:null,educationYearId:null,description:null,type:null}),[s,I]=(0,a.useState)([]),R=()=>n(!1),A=(0,S.useSelector)(e=>{var n;return null==e?void 0:null===(n=e.educationYear)||void 0===n?void 0:n.educationYear})||null,z=(0,S.useSelector)(e=>{var n;return null==e?void 0:null===(n=e.student)||void 0===n?void 0:n.student})||null,F=(0,S.useDispatch)(),{headers:M}=(0,r.getHeaders)(),[U,L]=(0,a.useState)(!0),[B,q]=(0,a.useState)([]),H=async()=>{await N.default.get("".concat(r.BASE_URL,"/reference/checkPreference?studentId=").concat(z.id)).then(e=>{var t;(null===(t=e.data)||void 0===t?void 0:t.success)?n(!0):C.toast.warning("File exist")}).catch(e=>{C.toast.error("Error")})},K=async()=>{await N.default.get(r.BASE_URL+"/education/educationYearsForSelected",{headers:M}).then(e=>{var n,t;F((0,w.fetchEducationYear)(null==e?void 0:null===(t=e.data)||void 0===t?void 0:null===(n=t.obj)||void 0===n?void 0:n[0]))}).catch(e=>{})},P=async()=>{await N.default.get("".concat(r.BASE_URL,"/reference/getTypesOfReference")).then(e=>{var n;I(null==e?void 0:null===(n=e.data)||void 0===n?void 0:n.obj)}).catch(e=>{})};(0,a.useEffect)(()=>{K(),P()},[]);let V=async()=>{await N.default.post("".concat(r.BASE_URL,"/reference/create"),{...t,studentId:null==z?void 0:z.id,deanId:null==z?void 0:z.deanId,educationYearId:null==A?void 0:A.id},{headers:M}).then(e=>{var n;R(),C.toast.success(null==e?void 0:null===(n=e.data)||void 0===n?void 0:n.message)}).catch(e=>{})},J=async e=>{e.preventDefault(),(null==t?void 0:t.description)&&(null==t?void 0:t.type)&&await V()};return(0,a.useEffect)(()=>{if(!U){let e=new EventSource(r.BASE_URL+"/reference/stream?userId="+(null==z?void 0:z.id)+"&bool=false");return e.addEventListener("reference-list-event",e=>{q(JSON.parse(e.data))}),e.onerror=()=>{e.close()},()=>{e.close()}}},[U]),(0,a.useEffect)(()=>{(null==z?void 0:z.id)&&L(!1)},[z]),(0,l.jsxs)(l.Fragment,{children:[(0,l.jsx)(d.Card,{sx:{mt:3},children:(0,l.jsxs)(d.CardContent,{children:[(0,l.jsx)(d.Stack,{mt:1,direction:"row",justifyContent:"end",children:(0,l.jsx)(o.default,{onClick:()=>H(),variant:"contained",color:"success",endIcon:(0,l.jsx)(c.MdLibraryAdd,{}),children:"Apply"})}),(0,l.jsx)(D,{children:(0,l.jsxs)("table",{children:[(0,l.jsx)("thead",{children:(0,l.jsxs)("tr",{children:[(0,l.jsx)("th",{children:"\u2116"}),(0,l.jsx)("th",{children:"Type"}),(0,l.jsx)("th",{children:"Date"}),(0,l.jsx)("th",{children:"Description"}),(0,l.jsx)("th",{children:"Status"}),(0,l.jsx)("th",{children:"File"})]})}),(0,l.jsx)("tbody",{children:B.length>0&&(null==B?void 0:B.map((e,n)=>(0,l.jsxs)("tr",{children:[(0,l.jsx)("td",{style:{textAlign:"center"},children:n+1}),(0,l.jsx)("td",{children:null==e?void 0:e.type}),(0,l.jsx)("td",{children:k()(new Date(null==e?void 0:e.createdAt)).format("DD-MM-YYYY HH:mm:ss")}),(0,l.jsx)("td",{children:null==e?void 0:e.description}),(0,l.jsx)("td",{children:(0,l.jsx)(d.Stack,{direction:"row",justifyContent:"center",children:(0,l.jsx)(o.default,{size:"small",variant:"contained",color:(null==e?void 0:e.status)==="AT_PROCESS"?"warning":(null==e?void 0:e.status)==="CONFIRM"?"success":(null==e?void 0:e.status)==="REJECT"?"error":"default",children:null==e?void 0:e.status})})}),(0,l.jsx)("td",{style:{display:"flex",alignItems:"center",justifyContent:"center"},children:(null==e?void 0:e.status)==="CONFIRM"?(0,l.jsx)(_.default,{id:null==e?void 0:e.id}):(0,l.jsx)(d.Stack,{direction:"row",justifyContent:"center",width:"100%",children:(0,l.jsx)(o.default,{disabled:(null==e?void 0:e.status)!=="CONFIRM",variant:"outlined",size:"small",style:{display:"flex",alignItems:"center",justifyContent:"center",padding:"10px",cursor:"not-allowed"},children:(0,l.jsx)(b.BsDownload,{})})})})]},n)))})]})}),0===B.length&&(0,l.jsx)(f.default,{children:(0,l.jsx)(E.default,{w:200,h:180})})]})}),(0,l.jsx)(T.default,{}),(0,l.jsx)(h.default,{open:e,onClose:R,"aria-labelledby":"modal-modal-title","aria-describedby":"modal-modal-description",children:(0,l.jsxs)(f.default,{sx:Y,children:[(0,l.jsxs)(O,{children:[(0,l.jsx)(u.default,{id:"modal-modal-title",variant:"h6",component:"h2",children:"Apply"}),(0,l.jsx)(x.default,{onClick:R,"aria-label":"close",size:"medium",children:(0,l.jsx)(p.CgClose,{})})]}),(0,l.jsx)(f.default,{children:(0,l.jsxs)("form",{onSubmit:J,children:[(0,l.jsxs)(v.default,{sx:{mt:3},required:!0,fullWidth:!0,children:[(0,l.jsx)(m.default,{id:"demo-simple-select-label",children:"Type"}),(0,l.jsx)(g.default,{labelId:"demo-simple-select-label",id:"demo-simple-select",value:t.type,label:"Type",onChange:e=>i(n=>({...n,type:e.target.value})),children:null==s?void 0:s.map((e,n)=>(0,l.jsx)(j.default,{disabled:B.some(n=>n.type===e&&"CONFIRM"===n.status),value:e,children:e},n))})]}),(0,l.jsx)(y.default,{sx:{mt:3},fullWidth:!0,required:!0,value:t.description,onChange:e=>i(n=>({...n,description:e.target.value})),id:"description",label:"Description",multiline:!0,rows:2}),(0,l.jsxs)(d.Stack,{sx:{mt:3},direction:"row",spacing:3,justifyContent:"end",children:[(0,l.jsx)(o.default,{variant:"outlined",type:"reset",onClick:R,children:"cancel"}),(0,l.jsx)(o.default,{variant:"contained",type:"submit",children:"save"})]})]})})]})})]})}}}]);