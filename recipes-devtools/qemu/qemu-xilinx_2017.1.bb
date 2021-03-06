QEMU_TARGETS = "aarch64 arm microblaze microblazeel"

require recipes-devtools/qemu/qemu.inc

SUMMARY = "Xilinx's fork of a fast open source processor emulator"
HOMEPAGE = "https://github.com/xilinx/qemu/"

LIC_FILES_CHKSUM = " \
		file://COPYING;md5=441c28d2cf86e15a37fa47e15a72fbac \
		file://COPYING.LIB;endline=24;md5=c04def7ae38850e7d3ef548588159913 \
		"

SRCREV = "45d810957b0f837a5685fbe4bc8d9e3268c1fe64"
SRC_URI = "git://github.com/Xilinx/qemu.git;protocol=https;nobranch=1"

S = "${WORKDIR}/git"

# Disable KVM completely
PACKAGECONFIG_remove = "kvm"

# Enable libgcrypt
PACKAGECONFIG_append = " gcrypt"

DISABLE_STATIC_pn-${PN} = ""

PTEST_ENABLED = ""

# append a suffix dir, to allow multiple versions of QEMU to be installed
EXTRA_OECONF_append = " \
		--bindir=${bindir}/qemu-xilinx \
		--libexecdir=${libexecdir}/qemu-xilinx \
		"

do_configure_prepend() {
	# rewrite usage of 'libgcrypt-config' with 'pkg-config libgcrypt'
	sed -r -i 's/libgcrypt-config(\s*--)/pkg-config libgcrypt\1/g' ${S}/configure
}

do_install_append() {
	# Prevent QA warnings about installed ${localstatedir}/run
	if [ -d ${D}${localstatedir}/run ]; then rmdir ${D}${localstatedir}/run; fi
}

