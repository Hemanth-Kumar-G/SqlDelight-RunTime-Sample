import SwiftUI
import shared

struct ContentView: View {
    let greet = Greeting().greet()
    
    var body: some View {
        Text(greet)
            .onAppear{
                let driver =  DatabaseDriverFactory(dbName: "skbndjs").createDriver()
                
                driver.executeQuery(
                    identifier: nil,
                    sql: "select * from Per",
                    mapper: { cursor in
                        print(cursor.next())
                    },
                    parameters: 0)
            }
    }
}

struct ContentView_Previews: PreviewProvider {
    static var previews: some View {
        ContentView()
    }
}
