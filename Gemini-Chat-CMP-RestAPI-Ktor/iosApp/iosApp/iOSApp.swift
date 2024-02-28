import SwiftUI
import ComposeApp

@main
struct iOSApp: App {

    init() {
        ModulesKt.doInitKoin()
    }

	var body: some Scene {
		WindowGroup {
			ContentView()
		}
	}
}