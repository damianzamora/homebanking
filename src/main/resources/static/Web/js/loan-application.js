const app = Vue.createApp({
    data (){
        return {
            cliente:[],
            arrayLoans:[],
            arrayAccounts:[],
            amount:"",
            cuentaDestino:"",
            tipoPrestamo:"",
            cuotasSeleccionadas:"",
            porcent:"",
        };
    },
    created(){
            axios.get('/api/clients/current')
            .then(res=> {
             this.cliente=res.data
             this.arrayAccounts=res.data.accounts.filter(e=>e.active==true)
             
            })
            axios.get('/api/loans')
            .then(res=> {
             this.arrayLoans=res.data
             
            })
    },
    methods:{
        saldo(tipo){
            prueba=this.arrayLoans.filter((e=>e.name==tipo))
            return prueba[0].maxAmount
        },
        cuotas(tipo){
            prueba=this.arrayLoans.filter((e=>e.name==tipo))
            return prueba[0].payments
        },
        
        porcentaje(tipo){
            prueba=this.arrayLoans.filter((e=>e.name==tipo))
            this.porcent=prueba[0].porcent
            return prueba[0].porcent
        },
        calcularValorCuota(){
            if (this.amount<0 || this.amount>1000000) 
            {
                return "-"
            }
            else{
            interes= (this.amount*this.porcent )/Number(100)
            subtotal=Number(this.amount)+Number(interes)                       
            return (Number(subtotal)/Number(this.cuotasSeleccionadas)).toFixed(2)
             }
             return 0;

        },
        calcularImporteconInteres(){
            if(this.amount> 1000000 || this.amount<=0)
            {
                return "-"
            }
            else{
                interes= (this.amount*this.porcent )/Number(100)
                subtotal=Number(this.amount)+Number(interes)
                return Number(subtotal)
            }
            return 0 ;
           

        },
        mostrar(){
            if(this.amount > 1000000 || this.amount <0)
            {
                
                return "-"
            }
            else
            return this.amount
        },
        CrearSolicitudPrestamo(){
            
            Swal.fire({
                title: 'Desea pedir el prestamo?',
                text: "Una vez realizada, no se podra revertir",
                icon: 'warning',
                showCancelButton: true,
                confirmButtonColor: '#3085d6',
                cancelButtonColor: '#d33',
                confirmButtonText: 'Si!'
              }).then((result) => {
                if (result.isConfirmed) {
                    axios.post('/api/clients/current/loans',
                    { "amountInicial":this.amount,"name":this.tipoPrestamo, "payments":this.cuotasSeleccionadas,"porcent":this.porcent,
                    "accountDestiny":this.cuentaDestino},
                    )
                    .then(res => Swal.fire(
                   'Préstamo solicitado con exito!',
                    'Se deposito el dinero en la cuenta seleccionada.',
                    'success',))
                    .then(res=>location.reload())
                    .catch(res => Swal.fire(res.response.data,"Intente nuevamente","error"))
                  
                }
              })
            
        
        },
        

        logout(){
            axios.post('/api/logout').then(response => console.log('signed out!!!'))
            .then(response=>window.location.href = "index.html")
        },
    },
    
});

app.mount("#app"); 