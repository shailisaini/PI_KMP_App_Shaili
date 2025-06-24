import SwiftUI

@main
struct iOSApp: App {

init(){
SetupKoinDiKt.doInitKoin()
}

	var body: some Scene {
		WindowGroup {
			ContentView()
		}
	}
}