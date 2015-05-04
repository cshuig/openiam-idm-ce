import java.text.SimpleDateFormat

println("================ accountExpirationDate.groovy starting..")

if (user.lastDate) {
    def sdf = new SimpleDateFormat("'yyyy/MM/dd")
    output = sdf.format(user.lastDate.time)
} else {
    output = '0'
}
println("================ accountExpirationDate.groovy output="+output)